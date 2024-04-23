package com.genxsol.elections

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.hasScrollToNodeAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToNode
import com.genxsol.elections.ui.base.ResultScreenUiState
import com.genxsol.elections.ui.base.ResultUiState
import com.genxsol.elections.ui.base.ShowError
import com.genxsol.elections.ui.base.ShowLoading
import com.genxsol.elections.ui.components.ResultsLayout
import org.junit.Rule
import org.junit.Test


class ResultsScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun loading_whenShowLoading_isShown() {
        composeTestRule.setContent {
            ShowLoading()
        }

        composeTestRule
            .onNodeWithContentDescription(composeTestRule.activity.resources.getString(R.string.loading))
            .assertExists()
    }

    @Test
    fun polls_whenResultsLayout_isShown() {
        composeTestRule.setContent {
            ResultsLayout(
                results = testResultUiState,
                itemClicked = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                testResultUiState.results[0].party,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testResultUiState.results[0].candidate,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testResultUiState.results[0].votes,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()
    }

    @Test
    fun error_whenShowError_isShown() {
        val errorMessage = "Error Message For You"

        composeTestRule.setContent {
            ShowError(
                text = errorMessage
            )
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertExists()
    }

}

private val testResultUiState =
    ResultScreenUiState(results =
    listOf(
        ResultUiState(party= "party1", candidate = "candidate1", "30", true),
        ResultUiState(party= "party2", candidate = "candidate2", "20", false),
        ResultUiState(party= "party3", candidate = "candidate3", "10", false),
    ),
        complete = false
    )
