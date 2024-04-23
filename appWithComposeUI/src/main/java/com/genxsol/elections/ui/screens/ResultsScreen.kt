package com.genxsol.elections.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.genxsol.elections.R
import com.genxsol.elections.common.NoInternetException
import com.genxsol.elections.ui.base.ResultScreenUiState
import com.genxsol.elections.ui.base.ShowError
import com.genxsol.elections.ui.base.ShowLoading
import com.genxsol.elections.ui.base.UIState
import com.genxsol.elections.ui.components.ResultsLayout
import com.genxsol.elections.ui.viewmodels.ElectionResultsViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ResultsScreen(
    electionResultsViewModel: ElectionResultsViewModel = hiltViewModel(),
    itemClicked: (String) -> Unit
) {
    val uiState: UIState<ResultScreenUiState> by electionResultsViewModel.uiState.collectAsStateWithLifecycle()

    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var countingFinished by rememberSaveable { mutableStateOf(false) }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = {
            if (!countingFinished) {
                isRefreshing = true
                electionResultsViewModel.fetchPollResults()
            }
        }
    )
    Scaffold(
        floatingActionButton = {
            if (uiState is UIState.Success && (uiState as UIState.Success).data.complete) {
                countingFinished = true
            } else {
                FloatingActionButton(onClick = {
                    if (uiState != UIState.Loading) {
                        isRefreshing = true
                        electionResultsViewModel.fetchPollResults()
                    }
                }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Refresh,
                        contentDescription = stringResource(id = R.string.refresh)
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .pullRefresh(pullRefreshState)
        ) {
            when (uiState) {
                is UIState.Loading -> {
                    if (!isRefreshing)
                        ShowLoading()
                }

                is UIState.Failure -> {
                    isRefreshing = false
                    var errorText = stringResource(id = R.string.something_went_wrong)
                    if ((uiState as UIState.Failure<*>).throwable is NoInternetException) {
                        errorText = stringResource(id = R.string.no_internet_available)
                    }
                    ShowError(
                        text = errorText,
                        retryEnabled = true
                    ) {
                        electionResultsViewModel.fetchPollResults()
                    }
                }

                is UIState.Success -> {
                    isRefreshing = false

                    val state = uiState as UIState.Success<ResultScreenUiState>
                    if (state.data.results.isEmpty()) {
                        ShowError(text = stringResource(R.string.no_data_available))
                    } else {
                        ResultsLayout(results = state.data) {
                            itemClicked(it)
                        }
                    }
                }
            }
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}
