package com.genxsol.elections.domain

import com.genxsol.elections.api.Results
import com.genxsol.elections.api.Result
import com.genxsol.elections.common.dispatcher.TestDispatcherProvider
import com.genxsol.elections.data.database.entity.CandidateEntity
import com.genxsol.elections.data.repository.ElectionsRepository
import com.genxsol.elections.ui.base.ResultItemUiState
import com.genxsol.elections.ui.base.ResultScreenUiState
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PollResultsWithCandidateNameUseCaseTest {

    @Mock
    lateinit var electionsRepository: ElectionsRepository
    lateinit var useCase: PollResultsWithCandidateNameUseCase

    @Before
    fun setUp() {
        val dispatcherProvider = TestDispatcherProvider()
        useCase = PollResultsWithCandidateNameUseCase(electionsRepository, dispatcherProvider)
    }

    @Test
    fun executePollResultsWithCandidateNameUseCaseReturnSuccess() {
        runBlocking {
            val results = Results(
                false,
                listOf(
                    Result(1, "party1", 100),
                    Result(2, "party2", 101)
                )
            )
            val candidates = listOf(
                CandidateEntity(1, "candidateName1"),
                CandidateEntity(2, "candidateName2")
            )
            val resultItemUIState = ResultScreenUiState(
                listOf(
                    ResultItemUiState("party2", "candidateName2", "101", true),
                    ResultItemUiState("party1", "candidateName1", "100", false),
                ),
                false
            )
            doReturn(flowOf(results))
                .`when`(electionsRepository).getPollResults()

            doReturn(flowOf(candidates))
                .`when`(electionsRepository).getAllCandidates()

            val resultScreenUiState = useCase.execute().first()

            assertEquals(resultItemUIState, resultScreenUiState)
        }

    }

}