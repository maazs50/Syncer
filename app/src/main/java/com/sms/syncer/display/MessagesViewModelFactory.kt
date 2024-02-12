package com.sms.syncer.display

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MessagesViewModelFactory(private val messagesRepo: MessagesRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessagesViewModel::class.java)){
            return MessagesViewModel(messagesRepo) as T
        }
        throw IllegalArgumentException("Unknown viewModel class")
    }
}