package com.prasad.abbreviationsfinder.view.ui.acronyms

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prasad.abbreviationsfinder.model.AcronymData
import com.prasad.abbreviationsfinder.repository.AbbreviationRepository
import com.prasad.abbreviationsfinder.repository.NetworkState
import com.prasad.abbreviationsfinder.retrofit.api.AbbreviationApiInterface
import com.prasad.abbreviationsfinder.utils.ValidationUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

/**
 *  This is MainViewModel class, which has complete business logic for fetching large forms,
 *  for the sort form provided by user, and display the list on screen.
 */
@HiltViewModel
class AbbreviationViewModel @Inject constructor(private val repo: AbbreviationRepository) :
    ViewModel() {

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val largeFormList = MutableLiveData<List<String>>()
    val loading = MutableLiveData(View.GONE)
    val rvVisibility = MutableLiveData(View.GONE)

    //API call to fetch meanings data for sortForm provided by user.
    fun getMeaningsData(sortForm: String) {
        viewModelScope.launch(Dispatchers.IO) {
            loading.postValue(View.VISIBLE)
            try {
                when (val response = repo.getMeaningsData(sortForm)) {
                    is NetworkState.Success -> {
                        getLargeFormsList(response.data)
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

    //Segregating large form list from MeaningsData response.
    private fun getLargeFormsList(acronymData: AcronymData) {
        if ((acronymData.isNotEmpty()) && (acronymData[0].longForms.isNotEmpty())) {
            val tempLfArrayList = mutableListOf<String>()
            for (lfItem in acronymData[0].longForms) {
                tempLfArrayList.add(lfItem.lf)
            }
            largeFormList.postValue(tempLfArrayList)
        } else {
            onError(ValidationUtil.RESPONSE_ERROR_MESSAGE)
        }
    }

    private fun onError(message: String) {
        _errorMessage.postValue(message)
        loading.postValue(View.GONE)
    }
}