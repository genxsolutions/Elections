package com.genxsol.elections.domain.usecase

import com.genxsol.elections.common.dispatcher.DispatcherProvider
import com.genxsol.elections.domain.model.ElectionsRepository
import com.genxsol.elections.domain.model.PollResultsWithCandidateNameState
import com.genxsol.elections.ui.base.ResultScreenUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext

class PollResultsWithCandidateNameUseCase(
    private val electionsRepository: ElectionsRepository,
    private val dispatcherProvider: DispatcherProvider
) {
    suspend fun execute(): Flow<ResultScreenUiState> {
        return withContext(dispatcherProvider.io) {
            try {
                val candidates = async { electionsRepository.getAllCandidates() }
                val pollResults = async { electionsRepository.getPollResults() }

                return@withContext PollResultsWithCandidateNameState(
                    pollResults.await(),
                    candidates.await()
                ).toResultUiState()
                    .catch { throwable -> throw Exception(throwable) }

            } catch (e: Exception) {
                throw e
            }
        }
    }
}