package com.example.bnet.utils

import androidx.room.Database
import com.example.bnet.app.App
import com.example.bnet.app.AppDatabase
import com.example.bnet.data.UserNoteRepository

import com.example.bnet.viewmodel.UserNotesViewModelFactory

object InjectorUtils {
    var database: AppDatabase = App.database
    fun provideUserNoteViewModelFactory() : UserNotesViewModelFactory {
        val userNoteRepository = UserNoteRepository.getInstance(database.userNoteDao())
        return  UserNotesViewModelFactory(userNoteRepository)
    }
}