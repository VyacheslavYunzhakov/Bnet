package com.example.bnet.app

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bnet.data.UserNote
import com.example.bnet.data.UserNoteDao

@Database(entities = [UserNote::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userNoteDao(): UserNoteDao
}
