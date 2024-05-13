package com.genxsol.elections.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genxsol.networkStatus.domain.NetworkMonitorUseCase
import com.genxsol.networkStatus.domain.NetworkState
import com.genxsol.elections.common.NoInternetException
import com.genxsol.elections.common.dispatcher.DispatcherProvider
import com.genxsol.elections.common.logger.Logger
import com.genxsol.elections.domain.PollResultsWithCandidateNameUseCase
import com.genxsol.elections.ui.base.ResultScreenUiState
import com.genxsol.elections.ui.base.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ElectionResultsViewModel @Inject constructor(
    private val pollResultsWithCandidateNameUseCase: PollResultsWithCandidateNameUseCase,
    private val networkMonitorUseCase: NetworkMonitorUseCase,
    private val logger: Logger,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState<ResultScreenUiState>>(UIState.Loading)
    val uiState: StateFlow<UIState<ResultScreenUiState>> = _uiState

    init {
        observeNetworkConnectivityAndLoad()
    }

    private fun observeNetworkConnectivityAndLoad() {
        viewModelScope.launch {
            networkMonitorUseCase.observeNetworkState()
                .collect {
                    when (it) {
                        NetworkState.Available -> fetchPollResultData()
                        NetworkState.Lost -> handleFetchError(NoInternetException())
                    }
                }
        }
    }

    fun fetchPollResults() {
        observeNetworkConnectivityAndLoad()
    }

    private fun fetchPollResultData() {
        viewModelScope.launch(dispatcherProvider.io) {
            try {
                // do not want to invoke automatically
                pollResultsWithCandidateNameUseCase.execute()
                    .collect { handleFetchSuccess(it) }
            } catch (e: Exception) {
                handleFetchError(e)
            }
        }
    }

    private suspend fun handleFetchError(throwable: Throwable) {
        _uiState.emit(UIState.Failure(throwable = throwable))
        logger.d("ElectionResultViewModel", "Error")
    }

    private suspend fun handleFetchSuccess(resultScreenUiState: ResultScreenUiState) {
        _uiState.emit(UIState.Success(resultScreenUiState))
        logger.d("ElectionResultViewModel", "Success")
    }
}

