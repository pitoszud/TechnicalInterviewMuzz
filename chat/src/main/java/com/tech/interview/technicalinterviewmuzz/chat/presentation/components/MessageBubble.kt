package com.tech.interview.technicalinterviewmuzz.chat.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.tech.interview.technicalinterviewmuzz.core.theme.ReceiverColor
import com.tech.interview.technicalinterviewmuzz.core.theme.SenderColor

@Composable
fun SenderMessageBubble(text: String) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 84.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp,
                        bottomStart = 18.dp,
                        bottomEnd = 0.dp
                    )
                )
                .background(color = SenderColor)
        ) {
            ChatMessage(
                text = text,
                textColor = Color.White
            )
        }
    }

}

@Composable
fun ReceiverMessageBubble(text: String
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .padding(end = 84.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 18.dp,
                        topEnd = 18.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 18.dp
                    )
                )
                .background(color = ReceiverColor)
        ) {
            ChatMessage(
                text = text,
                textColor = Color.Black
            )
        }
    }
}





@Composable
fun ChatMessage(
    text: String,
    paddingHorizontal: Dp = 16.dp,
    textColor: Color
) {
    Row(
        modifier = Modifier
            .padding(top = 12.dp, bottom = 12.dp, start = paddingHorizontal, end = 14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = text,
            color = textColor
        )
    }
}

@Preview
@Composable
fun SenderMessageBubblePreview() {
    SenderMessageBubble(text = "Does 7pm work for you? I've got to go pick up my little brother first from a party")
}

@Preview
@Composable
fun ReceiverMessageBubblePreview() {
    ReceiverMessageBubble(text = "What are you up to today?")
}