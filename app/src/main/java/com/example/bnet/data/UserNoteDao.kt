package com.example.bnet.data

import android.graphics.Movie
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface UserNoteDao {

    @Query("SELECT * FROM usernote ORDER BY modified DESC")
    fun getUserNotes(): LiveData<List<UserNote>>

    @Query("SELECT * FROM usernote")
    fun getUserNotesSimple():List<UserNote>

    @Insert
    fun addUserNote(userNote: UserNote)

    @Update
    fun updateUserNote(userNote: UserNote)

    @Query("DELETE FROM usernote")
    fun clearTable()
    /*
    private val  userNoteList = UserNotesList(mutableListOf<UserNote>())
    private val userNotes = MutableLiveData<UserNotesList>()

    init {
        userNotes.value = userNoteList
    }

    fun addUserNote(userNote: UserNote){
        userNoteList.add(userNote)
        userNotes.value = userNoteList
    }

     */

}