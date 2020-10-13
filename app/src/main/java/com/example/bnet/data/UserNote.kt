package com.example.bnet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
class UserNote(
    @PrimaryKey(autoGenerate = true)
    var id: Long,
    var text: String,
    var created: Long? = null,
    var modified: Long? = null):Serializable
