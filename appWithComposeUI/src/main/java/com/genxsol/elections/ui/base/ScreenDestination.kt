package com.genxsol.elections.ui.base
sealed class Screen(val route: String) {
    object ListScreen : Screen("list")
    object PopupScreen : Screen("popup")
}