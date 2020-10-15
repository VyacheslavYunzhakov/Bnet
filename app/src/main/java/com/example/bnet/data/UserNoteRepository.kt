package com.example.bnet.data


class UserNoteRepository private constructor (private val userNoteDao: UserNoteDao) {

    fun getUserNotesFromDatabase() = userNoteDao.getUserNotesFromDatabase()

    companion object {
        @Volatile
        private var instance: UserNoteRepository? = null

        fun getInstance(userNoteDao: UserNoteDao) =
            instance ?: synchronized(this) {
                instance ?: UserNoteRepository(userNoteDao).also { instance = it }
            }
    }

}