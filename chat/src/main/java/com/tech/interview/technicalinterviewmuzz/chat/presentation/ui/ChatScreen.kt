package com.tech.interview.technicalinterviewmuzz.chat.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import com.tech.interview.technicalinterviewmuzz.chat.presentation.components.ChatInput
import com.tech.interview.technicalinterviewmuzz.chat.presentation.vm.ChatViewModel
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import com.tech.interview.technicalinterviewmuzz.model.MessagePosition
import com.tech.interview.technicalinterviewmuzz.model.MessageType
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant


@Composable
fun ChatRoute(
    viewModel: ChatViewModel,
    onReturn: () -> Unit
) {

    LaunchedEffect(Unit) {
        viewModel.refreshMessages()
    }

    val uiState by viewModel.uiState.collectAsState()
    ChatScreen(
        messages = uiState.messages,
        isLoading = uiState.isLoading,
        submitTapped = {
            viewModel.onSendMessage(it)
        },
        onReturn = onReturn
    )
}


@Composable
fun ChatScreen(
    messages: List<ChatMessageUi>,
    isLoading: Boolean,
    scrollState: LazyListState = LazyListState(),
    submitTapped: (String) -> Unit,
    onReturn: () -> Unit
) {
    val viewScope = rememberCoroutineScope()
    if (messages.isNotEmpty())
        LaunchedEffect(Unit) {
            scrollState.animateScrollToItem(0)
        }

    var textState by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    if (isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = Color.Green,
            )
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars.only(WindowInsetsSides.Top))
                .windowInsetsPadding(WindowInsets.navigationBars.only(WindowInsetsSides.Bottom))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                TopRow(
                    onReturn = onReturn
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    ChatMessages(
                        messages = messages,
                        scrollState = scrollState
                    )
                }
                ChatInput(
                    userInput = textState,
                    onValueChange = {
                        textState = it
                    },
                    onSendMessage = {
                        keyboardController?.hide()
                        submitTapped(textState)
                        viewScope.launch {
                            delay(100)
                            textState = ""
                        }

                    }
                )
            }
        }

    }

}

@Composable
@Preview(showBackground = true)
fun ChatMessagesScreenPreview() {
    ChatScreen(
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
        ),
        isLoading = false,
        submitTapped = {},
        onReturn = {}
    )
}