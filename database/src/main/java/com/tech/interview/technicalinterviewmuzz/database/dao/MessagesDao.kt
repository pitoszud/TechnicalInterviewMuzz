package com.tech.interview.technicalinterviewmuzz.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tech.interview.technicalinterviewmuzz.database.entity.ChatMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MessagesDao {

    /**
     * Observes all messages.
     *
     * @return all messages.
     * */
    @Query("SELECT * FROM chat_messages ORDER BY timeStamp ASC")
    fun getAllMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessages(messages: List<ChatMessageEntity>)

    /**
     * Deletes all messages.
     * */
    @Query("DELETE FROM chat_messages")
    suspend fun deleteAll()

}