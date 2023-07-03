package com.prasad.abbreviationsfinder.view.ui.bookmark

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity
import com.prasad.abbreviationsfinder.repository.local.DBRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val dbRepository: DBRepository,
) : ViewModel() {
    val loading = MutableLiveData(View.GONE)
    val word = MutableLiveData("")
    val rvVisibility = MutableLiveData(View.GONE)
    val bookmarkFlowData: LiveData<MutableList<BookmarkEntity>> =
        dbRepository.getAllBookmarks().asLiveData()
    fun insertBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            dbRepository.insertBookmark(bookmark)
        }
    }
    fun deleteBookmark(bookmark: String) {
        viewModelScope.launch {
            dbRepository.delete(bookmark)
        }
    }
}