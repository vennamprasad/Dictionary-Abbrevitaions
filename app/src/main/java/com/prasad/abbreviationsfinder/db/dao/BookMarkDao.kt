package com.prasad.abbreviationsfinder.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BookMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(dictionaryEntity: BookmarkEntity): Long

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    @Query("SELECT * FROM DICTIONARY")
    fun getAllBookmarks(): Flow<MutableList<BookmarkEntity>>

    @Query("SELECT count(word) FROM DICTIONARY where word = :word")
    fun getWordCount(word: String): LiveData<Int?>

    @Delete
    suspend fun deleteBookmark(dictionaryEntity: BookmarkEntity)
}