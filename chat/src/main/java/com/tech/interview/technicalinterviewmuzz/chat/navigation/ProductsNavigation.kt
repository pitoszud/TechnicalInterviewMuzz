package com.tech.interview.technicalinterviewmuzz.chat.navigation


import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tech.interview.technicalinterviewmuzz.chat.presentation.ui.ChatRoute
import com.tech.interview.technicalinterviewmuzz.core.Screen

fun NavGraphBuilder.homeScreen(
    onReturn: () -> Unit
) {
    composable<Screen.Chat> {
        ChatRoute(
            viewModel = hiltViewModel(),
            onReturn = {
                onReturn()
            }
        )
    }
}