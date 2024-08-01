package com.tech.interview.technicalinterviewmuzz.database.converters

import androidx.room.TypeConverter
import kotlinx.datetime.Instant

class DatabaseTypeConverter {

    @TypeConverter
    fun fromTimestamp(value: Long?): Instant? {
        return value?.let { Instant.fromEpochMilliseconds(it) }
    }

    @TypeConverter
    fun dateToTimestamp(instant: Instant?): Long? {
        return instant?.toEpochMilliseconds()
    }
}