package com.genxsol.elections.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.genxsol.elections.R
import com.genxsol.elections.ui.base.ResultUiState

@Composable
fun ResultItem(result: ResultUiState, complete: Boolean, onItemClick: (String) -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onItemClick(result.party) }
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
                    Column(modifier = Modifier.padding(vertical = 5.dp)) {
                        Text(
                            text = stringResource(id = R.string.results_party, result.party),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(id = R.string.results_candidate, result.candidate)
                        )
                        Text(
                            text = stringResource(id = R.string.results_votes, result.votes)
                        )
                    }
                    if (complete && result.leading) {
                        WinnerBadge()
                    } else {
                        Spacer(modifier = Modifier.width(8.dp)) // Adjust the space as needed
                    }
                }
            }
        }
    }
}

@Composable
fun PartyImage(urlToImage: String, title: String?) {
    AsyncImage(
        model = urlToImage,
        error = painterResource(R.drawable.ic_icon),
        placeholder = painterResource(R.drawable.ic_icon),
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
            text = "Winner",
            style = TextStyle(fontWeight = FontWeight.Bold),
            color = Color.Black,
            modifier = Modifier.padding(start = 4.dp)
        )
    }
}