package com.tech.interview.technicalinterviewmuzz.database.di

import android.content.Context
import androidx.room.Room
import com.tech.interview.technicalinterviewmuzz.database.db.ChatDatabase
import com.tech.interview.technicalinterviewmuzz.database.dao.MessagesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesChatDatabase(
        @ApplicationContext context: Context,
    ): ChatDatabase = Room.databaseBuilder(
        context,
        ChatDatabase::class.java,
        "chat_db"
    ).build()

    @Provides
    fun providesMessagesDao(
        chatDatabase: ChatDatabase,
    ): MessagesDao = chatDatabase.chatMessageDao()
}