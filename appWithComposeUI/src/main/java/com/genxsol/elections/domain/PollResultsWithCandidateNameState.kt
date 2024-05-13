package com.genxsol.elections.domain

import com.genxsol.elections.ui.base.ResultItemUiState
import com.genxsol.elections.api.Results
import com.genxsol.elections.common.util.sortResultsByVotes
import com.genxsol.elections.data.database.entity.CandidateEntity
import com.genxsol.elections.ui.base.ResultScreenUiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

 class PollResultsWithCandidateNameState(
    private val pollResults: Flow<Results>,
    private val candidates: Flow<List<CandidateEntity>>
) {
    fun toResultUiState(): Flow<ResultScreenUiState> =

        pollResults.combine(candidates) { polls, candidatesList ->
            val resultUiStateList = polls.results.sortResultsByVotes().map { result ->
                val candidateName =
                    candidatesList.firstOrNull { it.id == result.candidateId }?.name ?: ""
                val leading =
                    polls.results.maxByOrNull { it.votes }?.candidateId == result.candidateId
                ResultItemUiState(
                    party = result.party,
                    candidate = candidateName,
                    votes = result.votes.toString(),
                    leading = leading
                )
            }
            ResultScreenUiState(resultUiStateList, polls.isComplete)
        }
}
