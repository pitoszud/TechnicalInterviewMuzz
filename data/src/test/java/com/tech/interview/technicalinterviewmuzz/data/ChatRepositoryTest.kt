package com.tech.interview.technicalinterviewmuzz.data

import com.tech.interview.technicalinterviewmuzz.data.fakes.FakeUserRepo
import com.tech.interview.technicalinterviewmuzz.database.fakes.FakeMessagesDao
import com.tech.interview.technicalinterviewmuzz.network.dto.ChatMessageDto
import com.tech.interview.technicalinterviewmuzz.network.fakes.FakeChatService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.Instant
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.tech.interview.technicalinterviewmuzz.data.utils.RepoConstants.ERROR_SYNC_DATA

@OptIn(ExperimentalCoroutinesApi::class)
class ChatRepositoryTest {

    private lateinit var chatService: FakeChatService
    private lateinit var messagesDao: FakeMessagesDao
    private lateinit var chatRepository: ChatRepository
    private lateinit var userRepo: FakeUserRepo


    private var testDispatcher = UnconfinedTestDispatcher()
    private var testScope = TestScope(testDispatcher)


    @Test
    fun `initial refresh should fetch data from remote data source`() = testScope.runTest {
        initRepository()
        // empty database
        val firstLoadItems = chatRepository.getMessagesStream().first()
        assertThat(firstLoadItems.size).isEqualTo(0)
        chatRepository.syncMessages()

        val secondLoadItems = chatRepository.getMessagesStream().first()
        assertThat(secondLoadItems.size).isEqualTo(8)
    }

    @Test
    fun `sending a message should update the local database`() = testScope.runTest {
        val message = "My message"
        initRepository()
        chatRepository.syncMessages()
        val firstLoadItems = chatRepository.getMessagesStream().first()
        assertThat(firstLoadItems.size).isEqualTo(8)
        chatRepository.sendMessage(message)
        val secondLoadItems = chatRepository.getMessagesStream().first()
        assertThat(secondLoadItems.size).isEqualTo(10) // There is a reply to the message
        assertThat(secondLoadItems[9].text).isEqualTo("This is a reply to message: $message")
    }


    @Test
    fun `when syncing messages throws an error, empty list should be returned`() = testScope.runTest {
            initRepository()
            chatService.setShouldThrowError(true)
            val result: Result<Unit> = chatRepository.syncMessages()
            assertThat(result.isFailure).isTrue()
            assertThat(result.exceptionOrNull()?.message).isEqualTo(ERROR_SYNC_DATA)

            val firstLoadItems = chatRepository.getMessagesStream().first()
            assertThat(firstLoadItems).isEmpty()
        }


    private fun initRepository() {
        chatService = FakeChatService(testRepoMessages)
        messagesDao = FakeMessagesDao()
        userRepo = FakeUserRepo()
        chatRepository = ChatRepository(
            dispatcher = testDispatcher,
            messagesDao = messagesDao,
            userRepository = userRepo,
            chatService = chatService
        )
    }
}


var testRepoMessages = listOf(
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
    )
)