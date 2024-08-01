package com.tech.interview.technicalinterviewmuzz.data

import com.tech.interview.technicalinterviewmuzz.database.entity.ChatMessageEntity
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import com.tech.interview.technicalinterviewmuzz.model.MessagePosition
import com.tech.interview.technicalinterviewmuzz.model.MessageType
import com.tech.interview.technicalinterviewmuzz.network.dto.ChatMessageDto

fun ChatMessageEntity.toChatMessageUi(userId: String) = ChatMessageUi(
    messageId = this.messageId,
    conversationId = this.conversationId,
    messagePosition = if (this.userGuid == userId) MessagePosition.RIGHT else MessagePosition.LEFT,
    messageType = if (this.userGuid == userId) MessageType.SENDER else MessageType.RECEIVER,
    timeStamp = this.timeStamp,
    text = this.text
)

fun ChatMessageDto.toChatEntity() = ChatMessageEntity(
    messageId = this.messageId,
    conversationId = this.conversationId,
    userGuid = this.userGuid,
    timeStamp = this.timeStamp,
    text = this.text
)

fun List<ChatMessageEntity>.toChatMessagesUi(userId: String): List<ChatMessageUi> = map { it.toChatMessageUi(userId) }

fun List<ChatMessageDto>.toChatMessageEntity(): List<ChatMessageEntity> = map(ChatMessageDto::toChatEntity)