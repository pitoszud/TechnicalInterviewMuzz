package com.tech.interview.technicalinterviewmuzz.model

import kotlinx.datetime.Instant


data class ChatMessageUi(
    val messageId: String,
    val conversationId: String,
    val messagePosition: MessagePosition,
    val messageType: MessageType,
    val timeStamp: Instant,
    val itemSection: String? = null,
    val text: String = ""
)

enum class MessagePosition {
    LEFT, RIGHT
}

enum class MessageType {
    SENDER,
    RECEIVER
}