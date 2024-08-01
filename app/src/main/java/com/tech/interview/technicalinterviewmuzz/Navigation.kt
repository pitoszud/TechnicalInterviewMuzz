package com.tech.interview.technicalinterviewmuzz

import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.tech.interview.technicalinterviewmuzz.chat.navigation.homeScreen
import com.tech.interview.technicalinterviewmuzz.core.Screen

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current as ComponentActivity

    BackHandler {
        context.finish()
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Chat
    ) {
        homeScreen(
            onReturn = {
                context.finish()
            }
        )
    }
}