package com.example.bnet.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserNoteDao {

    @Query("SELECT * FROM usernote ORDER BY modified DESC")
    fun getUserNotes(): LiveData<MutableList<UserNote>>
    //fun getUserNotes(): LiveData<UserNoteData>

    @Query("SELECT * FROM usernote")
    fun getUserNotesSimple():MutableList<UserNote>
    //fun getUserNotesSimple():UserNoteData

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