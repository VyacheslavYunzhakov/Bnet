package com.example.bnet.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bnet.data.UserNote
import com.example.bnet.data.UserNoteRepository


class UserNotesViewModel(private val userNoteRepository: UserNoteRepository): ViewModel() {

    fun getUserNotes() = userNoteRepository.getUserNotes()
    fun addUserNote(userNote: UserNote){
        userNoteRepository.addUserNote(userNote)
    }

}