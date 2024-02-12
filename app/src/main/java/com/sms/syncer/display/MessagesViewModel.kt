package com.sms.syncer.display

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MessagesViewModel(private val firebaseRepo: MessagesRepo) : ViewModel() {
    private val _messages = MutableLiveData<List<Message?>>()
    val messages = _messages

    fun loadMessages(){
        viewModelScope.launch {
            _messages.value = firebaseRepo.getAllMessage().value
        }
    }

    fun dummyData(){
        val data = listOf(
            Message("Msg1",123943879,"1903874","21-01-2024 09:51:38"),
            Message("Msg2",123943879,"1903874","21-01-2024 09:51:38"),
            Message("Msg3",123943879,"1903874","21-01-2024 09:51:38"),
            Message("Msg4",123943879,"1903874","21-01-2024 09:51:38"),
            Message("Msg5",123943879,"1903874","21-01-2024 09:51:38"),
            Message("Msg6",123943879,"1903874","21-01-2024 09:51:38"),
            Message("Msg7",123943879,"1903874","21-01-2024 09:51:38")
        )
        _messages.value = data
    }
}