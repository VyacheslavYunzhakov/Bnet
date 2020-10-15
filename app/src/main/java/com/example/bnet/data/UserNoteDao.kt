package com.example.bnet.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserNoteDao {

    @Query("SELECT * FROM usernote ORDER BY modified DESC")
    fun getUserNotesFromDatabase(): LiveData<MutableList<UserNote>>

    @Query("SELECT * FROM usernote")
    fun getUserNotesSimple():MutableList<UserNote>

    @Insert
    fun addUserNote(userNote: UserNote)

    @Update
    fun updateUserNote(userNote: UserNote)

    @Query("DELETE FROM usernote")
    fun clearTable()


}