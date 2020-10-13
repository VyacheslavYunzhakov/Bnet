package com.example.bnet.app

import android.app.Application
import androidx.room.Room.databaseBuilder
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

class App : Application() {
    companion object {
        lateinit var instance: App
        lateinit var database: AppDatabase
    }
    override fun onCreate()
    {
        super.onCreate()
        instance = this
        database = databaseBuilder(this, AppDatabase::class.java, "database")
            .allowMainThreadQueries()
            .build()
    }
}