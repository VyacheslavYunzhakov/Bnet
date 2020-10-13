package com.example.bnet.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bnet.data.UserNoteRepository

class UserNotesViewModelFactory (private val userNoteRepository: UserNoteRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T:ViewModel?> create (modelClass: Class<T>): T {
        return UserNotesViewModel(userNoteRepository) as T
    }
}