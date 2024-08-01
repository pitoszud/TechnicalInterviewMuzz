package com.tech.interview.technicalinterviewmuzz.database.fakes

import com.tech.interview.technicalinterviewmuzz.database.dao.MessagesDao
import com.tech.interview.technicalinterviewmuzz.database.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeMessagesDao(dbMessages: List<ChatMessageEntity>? = emptyList()) : MessagesDao {

    private var _messages: MutableMap<String, ChatMessageEntity>? = null
    private var messages: List<ChatMessageEntity>?
        get() = _messages?.values?.toList() ?: emptyList()
        set(newMessages) {
            _messages = newMessages?.associateBy { it.messageId }?.toMutableMap()
        }

    init {
        messages = dbMessages
    }

    override fun getAllMessages(): Flow<List<ChatMessageEntity>> = flow {
        messages?.let { emit(it) }
    }

    override suspend fun deleteAll() {
        _messages?.clear()
    }

    override suspend fun insertMessage(message: ChatMessageEntity) {
        _messages?.put(message.messageId, message)
    }

    override suspend fun insertMessages(messages: List<ChatMessageEntity>) {
        _messages?.putAll(messages.associateBy { it.messageId })
    }
}