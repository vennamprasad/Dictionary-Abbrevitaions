package com.prasad.abbreviationsfinder.view.ui.bookmark

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.prasad.abbreviationsfinder.db.entity.BookmarkEntity
import com.prasad.abbreviationsfinder.repository.local.DBRepository
import com.prasad.abbreviationsfinder.utils.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val dbRepository: DBRepository,
) : ViewModel() {
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    val bookmarkList : MutableLiveData<List<BookmarkEntity>> = MutableLiveData()
    val loading = MutableLiveData(View.GONE)
    val word = MutableLiveData("")
    val rvVisibility = MutableLiveData(View.GONE)
    val bookmarkFlowData : LiveData<MutableList<BookmarkEntity>> = dbRepository.getAllBookmarks().asLiveData()
/*
    fun getAllBookmarks() {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(View.VISIBLE)
            try {
                val response = dbRepository.getAllBookmarks()
                Log.d("HAHHAHA", "getAllBookmarks: ${response.value}")
                if (response.value?.isNotEmpty() == true) {
                    processData(response)
                    loading.postValue(View.GONE)
                } else {
                    onError(ValidationUtil.RESPONSE_ERROR_MESSAGE)
                }
            } catch (ex: UnknownHostException) {
                onError(ValidationUtil.NETWORK_ERROR_MESSAGE)
            } catch (ex: java.lang.Exception) {
                onError(ex.stackTraceToString())
            }
        }
    }
*/

    //Segregating large form list from MeaningsData response.
    private fun processData(response: LiveData<List<BookmarkEntity>>) {
        bookmarkList.postValue(response.value)
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
        loading.postValue(View.GONE)
    }

    fun insertBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            dbRepository.insertBookmark(bookmark)
        }
    }

    fun deleteBookmark(bookmark: BookmarkEntity) {
        viewModelScope.launch {
            dbRepository.delete(bookmark)
        }
    }
}