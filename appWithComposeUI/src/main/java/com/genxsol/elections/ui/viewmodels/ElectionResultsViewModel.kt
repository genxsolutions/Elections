package com.genxsol.elections.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.utilities.NetworkHelper
import com.genxsol.elections.common.NoInternetException
import com.genxsol.elections.common.dispatcher.DispatcherProvider
import com.genxsol.elections.common.logger.Logger
import com.genxsol.elections.common.util.sortResultsByVotes
import com.genxsol.elections.data.repository.ElectionsRepository
import com.genxsol.elections.ui.base.ResultScreenUiState
import com.genxsol.elections.ui.base.ResultUiState
import com.genxsol.elections.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElectionResultsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val electionsRepository: ElectionsRepository,
    private val dispatcherProvider: DispatcherProvider,
    private val networkHelper: NetworkHelper,
    private val logger: Logger,
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<ResultScreenUiState>>(UIState.Loading)
    val uiState: StateFlow<UIState<ResultScreenUiState>> = _uiState

    init {
        fetchPollResults()
    }

    fun fetchPollResults() {
        fetchPollResultData()
    }

    private fun fetchPollResultData() {
        viewModelScope.launch(dispatcherProvider.io) {
            if (!networkHelper.isNetworkConnected()) {
                _uiState.value = UIState.Failure(
                        throwable = NoInternetException()
                    )
                return@launch
            }
            val candidates = electionsRepository.getAllCandidates()
            val pollResults = electionsRepository.getPollResults()

            pollResults.combine(candidates) { polls, candidatesList ->
                val resultUiStateList = polls.results.sortResultsByVotes().map { result ->
                    val candidateName =
                        candidatesList.firstOrNull { it.id == result.candidateId }?.name ?: ""
                    val leading =
                        polls.results.maxByOrNull { it.votes }?.candidateId == result.candidateId
                    ResultUiState(
                        party = result.party,
                        candidate = candidateName,
                        votes = result.votes.toString(),
                        leading = leading
                    )
                }

                ResultScreenUiState(
                    resultUiStateList,
                    polls.isComplete
                )
            }
                .catch { throwable ->
                    _uiState.value = UIState.Failure(throwable = throwable)
                    logger.d("ElectionResultViewModel", "Error")
                }
                .collect { resultScreenUiState ->
                    _uiState.value = UIState.Success(resultScreenUiState)
                    logger.d("ElectionResultViewModel", "Success")
                }
        }
    }
}

