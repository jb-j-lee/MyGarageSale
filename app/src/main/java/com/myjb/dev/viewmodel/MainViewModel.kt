package com.myjb.dev.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myjb.dev.model.Repository
import com.myjb.dev.model.data.Company
import com.myjb.dev.model.remote.dto.BookInfoItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

internal class MainViewModel(val company: Company, private val repository: Repository) :
    ViewModel() {

    private val _list = MutableLiveData<List<BookInfoItem>>()
    val list: LiveData<List<BookInfoItem>>
        get() = _list

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean>
        get() = _progress


    fun getBooks(text: String) {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getBooks(text).onStart {
                _progress.value = true
            }.onCompletion {
                _progress.value = false
            }.collect {
                _list.value = it
            }
        }
    }
}