package com.prasad.abbreviationsfinder.repository.local

import androidx.lifecycle.LiveData
import com.prasad.abbreviationsfinder.db.AppDatabase
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class DBRepository @Inject constructor(private val appDatabase: AppDatabase) {

    suspend fun insertBookmark(dictionaryEntity: BookmarkEntity): Long {
        return appDatabase.bookmarksDao()
            .insertBookmark(dictionaryEntity)
    }

    suspend fun delete(dictionaryEntity: BookmarkEntity) {
        appDatabase.bookmarksDao().deleteBookmark(dictionaryEntity)
    }

    // NOTE - Since we are already using LIVE-DATA no need to use suspend function
    fun getAllBookmarks(): Flow<MutableList<BookmarkEntity>> {
        return appDatabase.bookmarksDao().getAllBookmarks()
    }

    fun getWordCount(word:String): LiveData<Int?> {
        return appDatabase.bookmarksDao().getWordCount(word)
    }
}