package com.genxsol.elections.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.genxsol.elections.R
import com.genxsol.elections.ui.base.ResultScreenUiState


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ResultsLayout(
    results: ResultScreenUiState,
    itemClicked: (String) -> Unit
) {
    LazyColumn {
        if (results.complete) {
            item {
                CountingFinished()
                Divider(thickness = 1.dp)
            }
        }
        items(results.results.size) { index ->
            AnimatedResultItem(result = results.results[index], complete = results.complete){
                itemClicked(it)
            }
            Divider(thickness = 1.dp)
        }
    }
}
@Composable
private fun CountingFinished(){
    Text(
        text = stringResource(R.string.counting_finished),
        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}
