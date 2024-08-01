package com.tech.interview.technicalinterviewmuzz.network.fakes

import com.tech.interview.technicalinterviewmuzz.network.dto.ChatMessageDto
import com.tech.interview.technicalinterviewmuzz.network.dto.UserMessageResponse
import com.tech.interview.technicalinterviewmuzz.network.service.ChatServiceApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow

class FakeChatService(private val initialMessages: List<ChatMessageDto>) : ChatServiceApi {

    private var shouldThrowError = false

    private val _messages: MutableStateFlow<List<ChatMessageDto>> = MutableStateFlow(initialMessages)
    private val messages = _messages.asStateFlow()

    override suspend fun sendMessage(message: ChatMessageDto): Result<UserMessageResponse> {
        val dummyReply = message.copy(
            messageId = "msg_123",
            userGuid = "usr_002",
            text = "This is a reply to message: ${message.text}"
        )
        val updatedMessages = messages.value.toMutableList().apply {
            add(message)
            add(dummyReply)
        }
        _messages.value = updatedMessages

        return Result.success(UserMessageResponse(id = message.userGuid))
    }

    override fun establishSocketConnection() {
        // Simulate establishing a socket connection
    }

    override fun closeSocketConnection() {
        // Simulate closing a socket connection
    }

    override suspend fun observeMessages(): Flow<List<ChatMessageDto>> = flow {
        if (shouldThrowError) {
            emit(emptyList())
        } else {
            emit(messages.value)
        }
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }
}