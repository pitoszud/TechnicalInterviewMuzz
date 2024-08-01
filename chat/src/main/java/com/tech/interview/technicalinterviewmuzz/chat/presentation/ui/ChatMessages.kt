package com.tech.interview.technicalinterviewmuzz.chat.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tech.interview.technicalinterviewmuzz.chat.presentation.components.AnimatedMessageBubble
import com.tech.interview.technicalinterviewmuzz.chat.presentation.components.ReceiverMessageBubble
import com.tech.interview.technicalinterviewmuzz.chat.presentation.components.SenderMessageBubble
import com.tech.interview.technicalinterviewmuzz.chat.presentation.components.TimeSection
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import com.tech.interview.technicalinterviewmuzz.model.MessagePosition
import com.tech.interview.technicalinterviewmuzz.model.MessageType
import kotlinx.datetime.Instant

@Composable
fun ChatMessages(
    messages: List<ChatMessageUi>,
    scrollState: LazyListState,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            state = scrollState,
            contentPadding = PaddingValues(
                bottom = 24.dp,
                top = 50.dp
            ),
            reverseLayout = true,
            verticalArrangement = Arrangement.Top,
        ) {
            items(
                messages.size,
                { index -> messages[messages.lastIndex - index].messageId }
            ) { index: Int ->
                val msg = messages[messages.lastIndex - index]

                AnimatedMessageBubble(
                    messageId = msg.messageId,
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                ) {
                    when (msg.messageType) {
                        MessageType.SENDER -> {
                            SenderMessageBubble(msg.text)
                        }
                        MessageType.RECEIVER -> {
                            ReceiverMessageBubble(msg.text)
                        }
                    }
                }
                msg.itemSection?.let {
                    TimeSection(it)
                }
            }
        }

    }
}


@Composable
@Preview(showBackground = true)
fun ChatMessagesPreview() {
    ChatMessages(
        messages = listOf(
            ChatMessageUi(
                messageId = "001",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.RIGHT,
                messageType = MessageType.SENDER,
                timeStamp = Instant.parse("2024-07-31T00:00:01Z"),
                text = "Wowsa so cool"
            ),
            ChatMessageUi(
                messageId = "002",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.LEFT,
                messageType = MessageType.RECEIVER,
                timeStamp = Instant.parse("2024-07-31T00:00:03Z"),
                text = "Yeh for sure that works. What time do you think?"
            ),
            ChatMessageUi(
                messageId = "003",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.RIGHT,
                messageType = MessageType.SENDER,
                timeStamp = Instant.parse("2024-07-31T00:00:05Z"),
                text = "Does 7pm work for you? I've got to go pick up my little brogther first from a party"
            ),
            ChatMessageUi(
                messageId = "004",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.LEFT,
                messageType = MessageType.RECEIVER,
                timeStamp = Instant.parse("2024-07-31T00:00:07Z"),
                text = "Ok cool!"
            ),
            ChatMessageUi(
                messageId = "005",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.RIGHT,
                messageType = MessageType.SENDER,
                timeStamp = Instant.parse("2024-07-31T01:00:09Z"),
                text = "What are you up to today?"
            ),
            ChatMessageUi(
                messageId = "006",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.LEFT,
                messageType = MessageType.RECEIVER,
                timeStamp = Instant.parse("2024-07-31T01:00:12Z"),
                text = "Nothing much"
            ),
            ChatMessageUi(
                messageId = "007",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.RIGHT,
                messageType = MessageType.SENDER,
                timeStamp = Instant.parse("2024-07-31T01:00:17Z"),
                text = "Actually just about to go shopping, got any recommendations for a good shoe shop? I'm a fashion disaster"
            ),
            ChatMessageUi(
                messageId = "008",
                conversationId = "conversation1_001",
                messagePosition = MessagePosition.RIGHT,
                messageType = MessageType.SENDER,
                timeStamp = Instant.parse("2024-07-31T01:00:18Z"),
                text = "The last one went on for hours"
            )
        ),
        scrollState = LazyListState()
    )
}