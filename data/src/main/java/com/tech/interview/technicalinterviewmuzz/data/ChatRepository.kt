package com.tech.interview.technicalinterviewmuzz.data

import com.tech.interview.technicalinterviewmuzz.core.di.DefaultDispatcher
import com.tech.interview.technicalinterviewmuzz.data.utils.RepoConstants.ERROR_SYNC_DATA
import com.tech.interview.technicalinterviewmuzz.database.dao.MessagesDao
import com.tech.interview.technicalinterviewmuzz.model.ChatMessageUi
import com.tech.interview.technicalinterviewmuzz.network.dto.ChatMessageDto
import com.tech.interview.technicalinterviewmuzz.network.service.ChatServiceApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.datetime.Clock
import java.util.UUID
import javax.inject.Inject

class ChatRepository @Inject constructor(
    private val chatService: ChatServiceApi,
    private val messagesDao: MessagesDao,
    private val userRepository: UserRepo,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
) : ChatRepo {

    private var conversationId = ""

    override fun getMessagesStream(): Flow<List<ChatMessageUi>> {
        return messagesDao.getAllMessages().map {
            withContext(dispatcher) {
                it.toChatMessagesUi(userRepository.getUserId())
            }
        }.catch { e ->
            // TODO - log error
            emit(emptyList())
        }
    }

    override suspend fun syncMessages(): Result<Unit> {
        return chatService.observeMessages().map { remoteMessages ->
            if (remoteMessages.isEmpty()) {
                conversationId = ""
                Result.failure(Exception(ERROR_SYNC_DATA))
            } else {
                withContext(dispatcher) {
                    val localMessages = messagesDao.getAllMessages().first()
                    val newMessages = remoteMessages.filter { remoteMessage ->
                        localMessages.none { localMessage -> localMessage.messageId == remoteMessage.messageId }
                    }
                    if (newMessages.isNotEmpty()) {
                        messagesDao.insertMessages(newMessages.toChatMessageEntity())
                    }
                    conversationId = remoteMessages.first().conversationId
                    Result.success(Unit)
                }
            }
        }.firstOrNull() ?: Result.failure(Exception(ERROR_SYNC_DATA))
    }

    override suspend fun sendMessage(message: String) {
        val newMessage = ChatMessageDto(
            messageId = UUID.randomUUID().toString(),
            conversationId = conversationId,
            userGuid = userRepository.getUserId(),
            text = message,
            timeStamp = Clock.System.now()
        )
        chatService.sendMessage(newMessage)
        syncMessages()
    }

    override fun establishSocketConnection() {
        chatService.establishSocketConnection()
    }

    override fun closeSocketConnection() {
        chatService.closeSocketConnection()
    }
}


interface ChatRepo {
    fun establishSocketConnection()
    fun closeSocketConnection()
    fun getMessagesStream(): Flow<List<ChatMessageUi>>
    suspend fun syncMessages(): Result<Unit>
    suspend fun sendMessage(message: String)
}