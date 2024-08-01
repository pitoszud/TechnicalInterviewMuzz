package com.tech.interview.technicalinterviewmuzz.chat

import androidx.lifecycle.SavedStateHandle
import com.google.common.truth.Truth.assertThat
import com.tech.interview.technicalinterviewmuzz.chat.presentation.vm.ChatViewModel
import com.tech.interview.technicalinterviewmuzz.data.fakes.FakeChatRepository
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import com.tech.interview.technicalinterviewmuzz.model.MessagePosition
import com.tech.interview.technicalinterviewmuzz.model.MessageType
import com.tech.interview.technicalinterviewmuzz.testing.MainCoroutineRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.datetime.Instant
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChatViewModelTest {
    private lateinit var viewModel: ChatViewModel

    private lateinit var repository: FakeChatRepository

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()


    @Before
    fun setupViewModel() {
        repository = FakeChatRepository()
        repository.addMessages(testChatMessages)
        viewModel = ChatViewModel(SavedStateHandle(), repository)
    }

    @Test
    fun `passing Instant should return formatted text`() {
        val result = viewModel.formatInstant(Instant.parse("2024-07-31T00:00:01Z"))
        val expected = "Wednesday 01:00"
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun `two instances separated by more than an hour should return true`() {
        val result = viewModel.isGreaterThanOneHour(
            Instant.parse("2024-07-31T00:00:01Z"),
            Instant.parse("2024-07-31T01:00:02Z")
        )
        assertThat(result).isTrue()
    }


    @Test
    fun `two instances separated by less than an hour should return false`() {
        val result1 = viewModel.isGreaterThanOneHour(
            Instant.parse("2024-07-31T00:00:01Z"),
            Instant.parse("2024-07-31T00:59:01Z"),
        )
        val result2 = viewModel.isGreaterThanOneHour(
            Instant.parse("2024-07-31T02:00:18Z"),
            Instant.parse("2024-07-31T02:00:18Z"),
        )
        assertThat(result1).isFalse()
    }

    @Test
    fun `messages with separated with timestamps greater than one hour should have item section`() {
        val messages = testChatMessages + ChatMessageUi(
            messageId = "009",
            conversationId = "conversation1_001",
            messagePosition = MessagePosition.RIGHT,
            messageType = MessageType.SENDER,
            timeStamp = Instant.parse("2024-07-31T03:00:18Z"),
            text = "Sorry for the late reply"
        )

        val result = viewModel.handleSection(messages)
        assertThat(result[0].itemSection).isNotNull()
        assertThat(result[1].itemSection).isNull()
        assertThat(result[2].itemSection).isNull()
        assertThat(result[3].itemSection).isNull()
        assertThat(result[4].itemSection).isNotNull()
        assertThat(result[5].itemSection).isNull()
        assertThat(result[6].itemSection).isNull()
        assertThat(result[7].itemSection).isNull()
        assertThat(result[8].itemSection).isNotNull()
    }


    @Test
    fun `first load of all chats should update state with default search and filtering`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        // refresh all messages
        viewModel.refreshMessages()
        assertThat(viewModel.uiState.first().isLoading).isTrue()
        advanceUntilIdle()
        assertThat(viewModel.uiState.first().isLoading).isFalse()

        assertThat(viewModel.uiState.first().errorMessage).isNull()
        assertThat(viewModel.uiState.first().messages.size).isEqualTo(8)
        assertThat(viewModel.uiState.first().messages[0].messageId).isEqualTo(testChatMessages[0].messageId)
        assertThat(viewModel.uiState.first().messages[7].messageId).isEqualTo(testChatMessages[7].messageId)
    }


    @Test
    fun `messages with timestamp differences greater than one hour should have item section`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher())

        // refresh all messages
        viewModel.refreshMessages()
        assertThat(viewModel.uiState.first().isLoading).isTrue()
        advanceUntilIdle()
        assertThat(viewModel.uiState.first().isLoading).isFalse()

        assertThat(viewModel.uiState.first().messages[0].itemSection).isNotNull()
        assertThat(viewModel.uiState.first().messages[1].itemSection).isNull()
        assertThat(viewModel.uiState.first().messages[2].itemSection).isNull()
        assertThat(viewModel.uiState.first().messages[3].itemSection).isNull()
        assertThat(viewModel.uiState.first().messages[4].itemSection).isNotNull()
        assertThat(viewModel.uiState.first().messages[5].itemSection).isNull()
        assertThat(viewModel.uiState.first().messages[6].itemSection).isNull()
        assertThat(viewModel.uiState.first().messages[7].itemSection).isNull()
    }



}



var testChatMessages = listOf(
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
)