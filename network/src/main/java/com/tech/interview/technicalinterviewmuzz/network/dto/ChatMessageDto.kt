package com.tech.interview.technicalinterviewmuzz.network.dto

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class ChatMessageDto(
    val messageId: String,
    val conversationId: String,
    val userGuid: String,
    val timeStamp: Instant,
    val text: String = ""
)