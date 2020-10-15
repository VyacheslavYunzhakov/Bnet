package com.example.bnet.api

import android.util.Log
import com.example.bnet.data.*
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class UserNotesRetriever {
    private val service: UserNoteService
    companion object {
        const val BASE_URL = "https://bnet.i-partner.ru/"
        const val TOKEN = "wKENgmJ-9g-aYvpsyB"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        service = retrofit.create(UserNoteService::class.java)
    }
    suspend fun getSessionId( formBody:  FormBody): SessionData{
        return service.retrieveSession(TOKEN,formBody)
    }

    suspend fun getUserNotes(formBody:  FormBody): UserNoteData {
        return service.retrieveUserNotes(TOKEN,formBody)
    }
    suspend fun addUserNote(formBody:  FormBody): UserNoteId {
        return service.addUserNote(TOKEN,formBody)
    }
}