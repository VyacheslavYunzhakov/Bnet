package com.example.bnet.data


class UserNoteRepository private constructor (private val userNoteDao: UserNoteDao) {

    fun addUserNote(userNote: UserNote){
        userNoteDao.addUserNote(userNote)
    }
    fun getUserNotes() = userNoteDao.getUserNotes()

    companion object {
        // Singleton instantiation you already know and love
        @Volatile
        private var instance: UserNoteRepository? = null

        fun getInstance(userNoteDao: UserNoteDao) =
            instance ?: synchronized(this) {
                instance ?: UserNoteRepository(userNoteDao).also { instance = it }
            }
    }

}