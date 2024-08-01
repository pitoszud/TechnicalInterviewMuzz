package com.tech.interview.technicalinterviewmuzz.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.interview.technicalinterviewmuzz.chat.R
import com.tech.interview.technicalinterviewmuzz.core.theme.SenderColor
import com.tech.interview.technicalinterviewmuzz.core.theme.SenderColorDisabled

@Composable
fun SendButton(
    enabled: Boolean,
    modifier: Modifier = Modifier,
    onSendMessage: () -> Unit
) {
    val painter = if (enabled)
        painterResource(id = R.drawable.ic_send) else
        painterResource(id = R.drawable.ic_send)

    val background = if (enabled) SenderColor else SenderColorDisabled

    val contentDesc = if (enabled) "send chat message enabled button" else "send chat message disabled button"

    IconButton(
        enabled = enabled,
        onClick = { onSendMessage() },
        modifier = modifier
            .background(color = background, shape = CircleShape)
            .size(56.dp)
    ) {
        Icon(
            modifier = Modifier,
            painter = painter,
            contentDescription = contentDesc,
            tint = Color.White
        )

    }
}

@Composable
@Preview
fun SendButtonActivePreview() {
    SendButton(enabled = true, onSendMessage = {})
}

@Composable
@Preview
fun SendButtonInactivePreview() {
    SendButton(enabled = false, onSendMessage = {})
}