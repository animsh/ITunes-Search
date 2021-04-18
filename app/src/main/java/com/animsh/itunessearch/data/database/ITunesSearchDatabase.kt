package com.animsh.itunessearch.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Created by animsh on 4/18/2021.
 */
@Database(
    entities = [ITunesSearchEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ResultTypeConverter::class)
abstract class ITunesSearchDatabase : RoomDatabase() {
    abstract fun iTunesSearchDao(): ITunesSearchDao
}