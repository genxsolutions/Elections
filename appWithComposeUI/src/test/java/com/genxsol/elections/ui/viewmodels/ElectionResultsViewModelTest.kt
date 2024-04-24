package com.genxsol.elections.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.example.utilities.NetworkHelper
import com.genxsol.elections.api.Result
import com.genxsol.elections.api.Results
import com.genxsol.elections.common.dispatcher.DispatcherProvider
import com.genxsol.elections.common.dispatcher.TestDispatcherProvider
import com.genxsol.elections.common.logger.Logger
import com.genxsol.elections.common.logger.TestLogger
import com.genxsol.elections.common.networkhelper.TestNetworkHelper
import com.genxsol.elections.data.database.entity.CandidateEntity
import com.genxsol.elections.data.repository.ElectionsRepository
import com.genxsol.elections.ui.base.ResultScreenUiState
import com.genxsol.elections.ui.base.ResultUiState
import com.genxsol.elections.ui.base.UIState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ElectionResultsViewModelTest {

    @Mock
    private lateinit var electionsRepository: ElectionsRepository

    private lateinit var logger: Logger
    private lateinit var dispatcherProvider: DispatcherProvider
    private lateinit var networkHelper: NetworkHelper

    @Before
    fun setUp() {
        logger = TestLogger()
        dispatcherProvider = TestDispatcherProvider()
        networkHelper = TestNetworkHelper()
        Dispatchers.setMain(dispatcherProvider.main)
    }

    @Test
    fun fetchResults_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {
            val results = Results(results = listOf(
                Result(candidateId = 1, party = "party1", votes= 20),
                Result(candidateId = 2, party = "party2", votes= 10),
                Result(candidateId = 3, party = "party3", votes= 30),
            ),
                isComplete = true
            )

            doReturn(flowOf(results))
                .`when`(electionsRepository)
                .getPollResults()

            val resultsUiStates = listOf(
                ResultUiState(
                    party = results.results[2].party, candidate = "candidate3", votes = "${results.results[2].votes}", leading = true
                ),
                ResultUiState(
                    party = results.results[0].party, candidate = "candidate1", votes = "${results.results[0].votes}", leading = false
                ),
                ResultUiState(
                    party = results.results[1].party, candidate = "candidate2", votes = "${results.results[1].votes}", leading = false
                )
            )

            val candidatesEntities = listOf(
                CandidateEntity(id= 1, name= "candidate1"),
                CandidateEntity(id= 2, name= "candidate2"),
                CandidateEntity(id= 3, name= "candidate3")
            )

            doReturn(flowOf(candidatesEntities))
                .`when`(electionsRepository)
                .getAllCandidates()

            val savedStateHandle = SavedStateHandle()

            val viewModel = ElectionResultsViewModel(
                savedStateHandle,
                electionsRepository,
                dispatcherProvider,
                networkHelper,
                logger,
            )
            viewModel.uiState.test {
                assertEquals(UIState.Success(ResultScreenUiState(resultsUiStates, true)), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
            verify(electionsRepository, Mockito.times(1)).getPollResults()
            verify(electionsRepository, Mockito.times(1)).getAllCandidates()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}