package com.prasad.abbreviationsfinder.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.prasad.abbreviationsfinder.db.dao.BookMarkDao
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity

@Database(
    version = 1,
    entities = [BookmarkEntity::class],
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun bookmarksDao(): BookMarkDao
}