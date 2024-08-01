package com.tech.interview.technicalinterviewmuzz.data.fakes

import androidx.annotation.VisibleForTesting
import com.tech.interview.technicalinterviewmuzz.data.ChatRepo
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update

class FakeChatRepository : ChatRepo {

    private var shouldThrowError = false

    private val _messages = MutableStateFlow(LinkedHashMap<String, ChatMessageUi>())
    private val messages: StateFlow<LinkedHashMap<String, ChatMessageUi>> = _messages.asStateFlow()


    private val messagesFlow: Flow<List<ChatMessageUi>> = messages.map {
        if (shouldThrowError) {
            throw Exception("Error Test")
        } else {
            it.values.toList()
        }
    }

    fun setShouldThrowError(value: Boolean) {
        shouldThrowError = value
    }

    override fun getMessagesStream(): Flow<List<ChatMessageUi>> = messagesFlow

    override suspend fun syncMessages(): Result<Unit> {
        return if (shouldThrowError) {
            Result.failure(Exception("Error Test"))
        } else {
            Result.success(Unit)
        }
    }

    @VisibleForTesting
    fun addMessages(messages: List<ChatMessageUi>) {
        _messages.update { oldMessages ->
            val newTasks = LinkedHashMap<String, ChatMessageUi>(oldMessages)
            for (prod in messages) {
                newTasks[prod.messageId] = prod
            }
            newTasks
        }
    }

    override suspend fun sendMessage(message: String) {

    }

    override fun establishSocketConnection() = Unit

    override fun closeSocketConnection() = Unit
}