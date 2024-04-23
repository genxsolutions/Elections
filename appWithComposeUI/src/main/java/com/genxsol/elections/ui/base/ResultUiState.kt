package com.genxsol.elections.ui.base

data class ResultUiState(
    val party: String,
    val candidate: String,
    val votes: String,
    val leading: Boolean
)
