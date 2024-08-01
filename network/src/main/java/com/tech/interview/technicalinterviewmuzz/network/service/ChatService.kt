package com.tech.interview.technicalinterviewmuzz.network.service

import com.tech.interview.technicalinterviewmuzz.network.dto.ChatMessageDto
import com.tech.interview.technicalinterviewmuzz.network.dto.UserMessageResponse
import io.ktor.client.HttpClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.datetime.Instant
import java.util.UUID
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

class ChatService @Inject constructor(
    private val client: HttpClient
) : ChatServiceApi {

    private var socketConnectionOpen = false


    private val _messages: MutableStateFlow<List<ChatMessageDto>> = MutableStateFlow(emptyList())
    private val messages = _messages.asStateFlow()


    override suspend fun observeMessages(): Flow<List<ChatMessageDto>> = flow {
        emit(existingMessages + messages.value)
}

    override fun establishSocketConnection() {
        socketConnectionOpen = true
    }

    override fun closeSocketConnection() {
        socketConnectionOpen = false
    }

    override suspend fun sendMessage(message: ChatMessageDto): Result<UserMessageResponse> {
        val dummyReply = message.copy(
            messageId = UUID.randomUUID().toString(),
            userGuid = "usr_002",
            timeStamp = addOneSecondToInstant(message.timeStamp),
            text = "This is reply to message: ${message.text}"
        )
        val updatedMessages = messages.value.toMutableList().apply {
            add(message)
            add(dummyReply)
        }
        _messages.value = updatedMessages

        return Result.success(UserMessageResponse(id = message.userGuid))
    }
}

fun addOneSecondToInstant(instant: Instant): Instant {
    return instant.plus(1.seconds)
}


interface ChatServiceApi {
    suspend fun observeMessages(): Flow<List<ChatMessageDto>>
    suspend fun sendMessage(message: ChatMessageDto): Result<UserMessageResponse>
    fun establishSocketConnection()
    fun closeSocketConnection()
}

var existingMessages = listOf(
    ChatMessageDto(
        messageId = "001",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T00:00:01Z"),
        text = "Wowsa so cool"
    ),
    ChatMessageDto(
        messageId = "002",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T00:00:03Z"),
        text = "Yeh for sure that works. What time do you think?"
    ),
    ChatMessageDto(
        messageId = "003",
        conversationId = "conversation1_001",
        userGuid = "usr_001",
        timeStamp = Instant.parse("2024-07-31T00:00:05Z"),
        text = "Does 7pm work for you? I've got to go pick up my little brogther first from a party"
    ),
    ChatMessageDto(
        messageId = "004",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T00:00:07Z"),
        text = "Ok cool!"
    ),
    ChatMessageDto(
        messageId = "005",
        conversationId = "conversation1_001",
        userGuid = "usr_001",
        timeStamp = Instant.parse("2024-07-31T00:00:09Z"),
        text = "What are you up to today?"
    ),
    ChatMessageDto(
        messageId = "006",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T00:00:12Z"),
        text = "Nothing much"
    ),
    ChatMessageDto(
        messageId = "007",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T00:00:17Z"),
        text = "Actually just about to go shopping, got any recommendations for a good shoe shop? I'm a fashion disaster"
    ),
    ChatMessageDto(
        messageId = "008",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T00:00:18Z"),
        text = "The last one went on for hours"
    ),


    ChatMessageDto(
        messageId = "009",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T02:00:20Z"),
        text = "Wowsa so cool"
    ),
    ChatMessageDto(
        messageId = "010",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T02:00:23Z"),
        text = "Yeh for sure that works. What time do you think?"
    ),
    ChatMessageDto(
        messageId = "011",
        conversationId = "conversation1_001",
        userGuid = "usr_001",
        timeStamp = Instant.parse("2024-07-31T02:00:24Z"),
        text = "Does 7pm work for you? I've got to go pick up my little brogther first from a party"
    ),
    ChatMessageDto(
        messageId = "012",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T02:00:25Z"),
        text = "Ok cool!"
    ),
    ChatMessageDto(
        messageId = "013",
        conversationId = "conversation1_001",
        userGuid = "usr_001",
        timeStamp = Instant.parse("2024-07-31T02:00:26Z"),
        text = "What are you up to today?"
    ),
    ChatMessageDto(
        messageId = "014",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T02:00:27Z"),
        text = "Nothing much"
    ),
    ChatMessageDto(
        messageId = "015",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T02:00:28Z"),
        text = "Actually just about to go shopping, got any recommendations for a good shoe shop? I'm a fashion disaster"
    ),
    ChatMessageDto(
        messageId = "016",
        conversationId = "conversation1_001",
        userGuid = "usr_002",
        timeStamp = Instant.parse("2024-07-31T02:00:29Z"),
        text = "The last one went on for hours"
    ),
)
