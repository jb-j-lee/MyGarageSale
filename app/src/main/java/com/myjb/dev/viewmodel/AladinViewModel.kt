package com.myjb.dev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjb.dev.model.Repository
import com.myjb.dev.model.data.Company
import com.myjb.dev.model.remote.datasource.APIResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class AladinViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val _result = MutableLiveData<APIResponse>()
    val result: LiveData<APIResponse>
        get() = _result

    fun getBooks(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getBooks(company = Company.ALADIN, text = text).collect {
                _result.value = it
            }
        }
    }
}