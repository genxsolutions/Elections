package com.genxsol.elections.ui.viewmodels

import app.cash.turbine.test
import com.genxsol.networkStatus.domain.NetworkMonitorUseCase
import com.genxsol.networkStatus.domain.NetworkState
import com.genxsol.elections.common.dispatcher.DispatcherProvider
import com.genxsol.elections.common.dispatcher.TestDispatcherProvider
import com.genxsol.elections.common.logger.Logger
import com.genxsol.elections.common.logger.TestLogger
import com.genxsol.elections.domain.PollResultsWithCandidateNameUseCase
import com.genxsol.elections.ui.base.ResultItemUiState
import com.genxsol.elections.ui.base.ResultScreenUiState
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
    private lateinit var networkMonitorUseCase: NetworkMonitorUseCase

    @Mock
    private lateinit var pollResultsWithCandidateNameUseCase: PollResultsWithCandidateNameUseCase

    private lateinit var logger: Logger
    private lateinit var dispatcherProvider: DispatcherProvider

    @Before
    fun setUp() {
        logger = TestLogger()
        dispatcherProvider = TestDispatcherProvider()
        Dispatchers.setMain(dispatcherProvider.main)
    }

    @Test
    fun fetchResults_whenRepositoryResponseSuccess_shouldSetSuccessUiState() {
        runTest {

            val resultScreenUiState = ResultScreenUiState(
                listOf(
                    ResultItemUiState(
                        "part1",
                        "candidate1",
                        "101",
                        true
                    ),
                    ResultItemUiState(
                        "part2",
                        "candidate2",
                        "100",
                        false
                    )
                ),
                false

            )
            val availableNetworkState = NetworkState.Available
            doReturn(flowOf(availableNetworkState))
                .`when`(networkMonitorUseCase)
                .observeNetworkState()

            doReturn(flowOf(resultScreenUiState))
                .`when`(pollResultsWithCandidateNameUseCase)
                .execute()

            val viewModel = ElectionResultsViewModel(
                pollResultsWithCandidateNameUseCase,
                networkMonitorUseCase,
                logger,
                dispatcherProvider,
            )
            viewModel.uiState.test {
                assertEquals(
                    UIState.Success(resultScreenUiState),
                    awaitItem()
                )
                cancelAndIgnoreRemainingEvents()
            }
            verify(networkMonitorUseCase, Mockito.times(1)).observeNetworkState()
            verify(pollResultsWithCandidateNameUseCase, Mockito.times(1)).execute()
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

}