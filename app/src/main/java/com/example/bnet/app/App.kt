package com.example.bnet.app

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room

import androidx.room.Room.databaseBuilder

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