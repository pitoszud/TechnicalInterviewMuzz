package com.tech.interview.technicalinterviewmuzz.chat.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import kotlinx.coroutines.delay

private const val msgDuration = 200

@Composable
fun AnimatedMessageBubble(
    messageId: String,
    modifier: Modifier = Modifier,
    messageComponent: @Composable () -> Unit
) {
    val isVisible = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = messageId) {
        delay(60L)
        isVisible.value = true
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible.value,
        enter = fadeInMessage(), // + slideInMessage(),
        exit = fadeOut()
    ) {
        messageComponent()
    }
}

@Composable
private fun fadeInMessage() = fadeIn(
    animationSpec = tween(
        durationMillis = msgDuration,
        delayMillis = msgDuration,
        easing = LinearOutSlowInEasing
    )
)