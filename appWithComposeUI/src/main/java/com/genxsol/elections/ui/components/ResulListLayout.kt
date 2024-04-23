package com.genxsol.elections.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.genxsol.elections.R
import com.genxsol.elections.ui.base.ResultScreenUiState


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ResultsLayout(
    results: ResultScreenUiState,
    itemClicked: (String) -> Unit
) {
    val focusRequester = remember { FocusRequester() }

    LazyColumn(
        modifier = Modifier
            .focusRequester(focusRequester)
            .fillMaxSize()
    ) {
        if (results.complete) {
            item {
                CountingFinished()
            }
        }
        items(results.results.size) { index ->
            AnimatedResultItem(result = results.results[index], complete = results.complete) {
                itemClicked(it)
            }
        }
    }
    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
private fun CountingFinished() {
    Text(
        text = stringResource(R.string.counting_finished),
        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Preview
@Composable
fun CountingFinishedPreview(){
    CountingFinished()
}