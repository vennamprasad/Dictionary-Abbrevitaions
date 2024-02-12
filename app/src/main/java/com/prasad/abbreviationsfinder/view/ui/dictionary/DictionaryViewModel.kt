package com.prasad.abbreviationsfinder.view.ui.dictionary

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prasad.abbreviationsfinder.model.Dictionary
import com.prasad.abbreviationsfinder.repository.DictionaryRepository
import com.prasad.abbreviationsfinder.repository.local.DBRepository
import com.prasad.abbreviationsfinder.utils.NetworkState
import com.prasad.abbreviationsfinder.utils.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryRepository: DictionaryRepository,
) : ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage
    val meaningsList = MutableLiveData<List<String>>()
    val loading = MutableLiveData(View.GONE)
    val word  =  MutableLiveData("")
    val rvVisibility = MutableLiveData(View.GONE)

    //API call to fetch meanings data for sortForm provided by user.
    fun getMeaningsData(word: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(View.VISIBLE)
            try {
                when (val response = dictionaryRepository.getDictionaryData(word)) {
                    is NetworkState.Success -> {
                        processData(response.data)
                        loading.postValue(View.GONE)
                    }

                    is NetworkState.Error -> {
                        onError(response.toString())
                    }
                }
            } catch (ex: UnknownHostException) {
                onError(ValidationUtil.NETWORK_ERROR_MESSAGE)
            } catch (ex: java.lang.Exception) {
                onError(ex.stackTraceToString())
            }
        }
    }

    //Segregating large form list from com.prasad.abbreviationsfinder.model.MeaningsData response.
    private fun processData(dictionary: List<Dictionary>) {
        if (dictionary.isNotEmpty()) {
            val tempLfArrayList = mutableListOf<String>()
            for (def in dictionary[0].meanings[0].definitions) {
                tempLfArrayList.add(def.definition.toString())
            }
            meaningsList.postValue(tempLfArrayList)
            word.postValue(dictionary[0].word)
        } else {
            onError(ValidationUtil.RESPONSE_ERROR_MESSAGE)
        }
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
        loading.postValue(View.GONE)
    }
}