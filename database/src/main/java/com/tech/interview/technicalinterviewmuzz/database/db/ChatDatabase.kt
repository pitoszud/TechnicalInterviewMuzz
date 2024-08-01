package com.tech.interview.technicalinterviewmuzz.database.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tech.interview.technicalinterviewmuzz.database.converters.DatabaseTypeConverter
import com.tech.interview.technicalinterviewmuzz.database.dao.MessagesDao
import com.tech.interview.technicalinterviewmuzz.database.entity.ChatMessageEntity

@Database(
    entities = [ChatMessageEntity::class],
    version = 1,
    exportSchema = true,
    autoMigrations = []
)
@TypeConverters(DatabaseTypeConverter::class)

abstract class ChatDatabase : RoomDatabase() {
    abstract fun chatMessageDao(): MessagesDao
}