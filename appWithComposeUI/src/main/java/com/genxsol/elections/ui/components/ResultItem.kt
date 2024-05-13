package com.genxsol.elections.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.genxsol.elections.R
import com.genxsol.elections.ui.base.ResultItemUiState

@Composable
fun ResultItem(result: ResultItemUiState, complete: Boolean, onItemClick: (String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(result.candidate) }
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
        ) {
            Box {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Adjust padding for touch target size
                    Column(modifier = Modifier.padding(vertical = 4.dp)) {
                        Title(
                            stringResource(id = R.string.results_party, result.party),
                            bold = true
                        )
                        Title(stringResource(id = R.string.results_candidate, result.candidate))
                        Title(stringResource(id = R.string.results_votes, result.votes))
                    }
                    if (complete && result.leading) {
                        WinnerBadge()
                    }
                }
            }
        }
    }
}

@Composable
fun Title(title: String, bold: Boolean = false) {
    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        modifier = Modifier.padding(4.dp),
        style = if (bold) {
            MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
        } else {
            MaterialTheme.typography.titleMedium
        }
    )
}

@Composable
fun PartyImage(urlToImage: String, title: String?) {
    AsyncImage(
        model = urlToImage,
        error = painterResource(R.drawable.ic_launcher_foreground),
        placeholder = painterResource(R.drawable.ic_launcher_foreground),
        contentDescription = title,
        contentScale = ContentScale.Crop,
        modifier = Modifier.width(150.dp)
    )
}

@Composable
fun WinnerBadge() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(Color.Red)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "",
                tint = Color.White,
                modifier = Modifier.padding(6.dp)
            )
        }
        Text(
            text = stringResource(R.string.winner),
            style = TextStyle(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}

@Preview
@Composable
fun ResultItemPreview() {
    val result = ResultItemUiState(
        party = "Party ABC",
        candidate = "John Doe",
        votes = "1000",
        leading = true
    )
    ResultItem(result = result, complete = true) {}
}