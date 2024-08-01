package com.tech.interview.technicalinterviewmuzz.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.Instant

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey
    val messageId: String,
    val conversationId: String,
    val userGuid: String,
    val timeStamp: Instant,
    val text: String = ""
)