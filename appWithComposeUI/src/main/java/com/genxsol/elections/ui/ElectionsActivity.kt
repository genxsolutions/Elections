package com.genxsol.elections.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.genxsol.elections.ui.base.MyNavHost
import com.genxsol.elections.ui.theme.ElectionAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ElectionsActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // supports light and dark mode
            ElectionAppTheme {
                Surface {
                    MyNavHost()
                }
            }
        }
    }

}