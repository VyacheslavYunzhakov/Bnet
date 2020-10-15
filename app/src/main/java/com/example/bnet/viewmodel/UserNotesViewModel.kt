package com.example.bnet.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bnet.api.UserNotesRetriever
import com.example.bnet.data.*
import okhttp3.FormBody


class UserNotesViewModel(private val userNoteRepository: UserNoteRepository): ViewModel() {

    var userNotesList: MutableLiveData<UserNoteData> = MutableLiveData()
    var userNotesId:MutableLiveData<UserNoteId> = MutableLiveData()
    var sessionList:MutableLiveData<SessionData> = MutableLiveData()

    fun getUserNotesFromDatabase() = userNoteRepository.getUserNotesFromDatabase()

    suspend fun initSession():MutableLiveData<SessionData>{
        val formBody =  FormBody.Builder()
            .add("a","new_session")
            .build()
        sessionList.value = UserNotesRetriever().getSessionId(formBody)
        return sessionList
    }

    suspend fun getUserNotes(sessionId: String?) :MutableLiveData<UserNoteData> {
        val formBody =  FormBody.Builder()
            .add("a","get_entries")//&session=$sessionId")
            .add("session", sessionId)
            .build()
        userNotesList.value = UserNotesRetriever().getUserNotes(formBody)
        return userNotesList
    }

    suspend fun addUserNoteToServer(sessionId: String?, body:String) :MutableLiveData<UserNoteId> {
        val formBody =  FormBody.Builder()
            .add("a","add_entry")//&session=$sessionId")
            .add("session", sessionId)
            .add("body", body)
            .build()
        userNotesId.value = UserNotesRetriever().addUserNote(formBody)
        return userNotesId
    }
}