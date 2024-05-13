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
import com.genxsol.elections.ui.base.ResultItemUiState
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
    fun polls_whenResultsLayout_isShown_whileCounting() {
        composeTestRule.setContent {
            ResultsLayout(
                results = testResultUiCountingState,
                itemClicked = {}
            )
        }

        composeTestRule
            .onNodeWithText(
                testResultUiCountingState.results[1].party,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule.onNode(hasScrollToNodeAction())
            .performScrollToNode(
                hasText(
                    testResultUiCountingState.results[1].candidate,
                    substring = true
                )
            )

        composeTestRule
            .onNodeWithText(
                testResultUiCountingState.results[1].votes,
                substring = true
            )
            .assertExists()
            .assertHasClickAction()

        composeTestRule
            .onNodeWithText(
                "Winner",
                substring = true
            )
            .assertDoesNotExist()

        composeTestRule
            .onNodeWithText(
                "Counting Finished",
                substring = true
            )
            .assertDoesNotExist()
    }

    @Test
    fun polls_whenCountingFinishedLayout_IsShown_CountingFinished() {
        composeTestRule.setContent {
            ResultsLayout(
                results = testResultUiCountingFinishedState,
                itemClicked = {}
            )
        }

        composeTestRule
            .onNodeWithText(
               "Winner",
                substring = true
            )
            .assertExists()

        composeTestRule
            .onNodeWithText(
                "Counting Finished",
                substring = true
            )
            .assertExists()
    }

    @Test
    fun polls_whenCountingFinishedLayout_IsShown_Refresh_Is_Hidden() {
        composeTestRule.setContent {
            ResultsLayout(
                results = testResultUiCountingFinishedState,
                itemClicked = {}
            )
        }
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

private val testResultUiCountingState =
    ResultScreenUiState(results =
    listOf(
        ResultItemUiState(party= "party1", candidate = "candidate1", "30", true),
        ResultItemUiState(party= "party2", candidate = "candidate2", "20", false),
        ResultItemUiState(party= "party3", candidate = "candidate3", "10", false),
    ),
        complete = false
    )

private val testResultUiCountingFinishedState = testResultUiCountingState.copy(complete = true)
