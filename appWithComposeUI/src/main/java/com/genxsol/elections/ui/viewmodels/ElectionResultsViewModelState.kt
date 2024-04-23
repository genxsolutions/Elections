package com.genxsol.elections.ui.viewmodels

import com.genxsol.elections.api.Candidate
import com.genxsol.elections.ui.base.ResultUiState
import com.genxsol.elections.api.Result
import com.genxsol.elections.ui.base.ResultScreenUiState

data class ElectionResultsViewModelState(
    val results: List<Result> = emptyList(),
    val candidates: List<Candidate> = emptyList(),
    val countingComplete: Boolean = false
) {

    fun toUiState() = ResultScreenUiState(
        results = results.map { result ->
            ResultUiState(
                party = result.party,
                candidate = result.candidateId.toString(),
                votes = result.votes.toString(),
                leading = false
            )
        },
        complete = countingComplete
    )
}
