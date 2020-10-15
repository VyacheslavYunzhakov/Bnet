package com.example.bnet.api

import com.example.bnet.data.SessionData
import com.example.bnet.data.UserNoteData
import com.example.bnet.data.UserNoteId
import okhttp3.FormBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header

import retrofit2.http.Headers


import retrofit2.http.POST

interface UserNoteService {

    @POST("testAPI/")
    suspend fun retrieveUserNotes(@Header("token") token:String, @Body formBody: FormBody): UserNoteData

    @POST("testAPI/")
    suspend fun retrieveSession(@Header("token") token:String, @Body formBody: FormBody): SessionData

    @POST("testAPI/")
    suspend fun addUserNote(@Header("token") token:String, @Body formBody: FormBody): UserNoteId

}
