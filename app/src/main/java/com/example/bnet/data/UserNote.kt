package com.example.bnet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class UserNote(
    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("body") var text: String,
    @SerializedName("da") var created: Long? = null,
    @SerializedName("dm") var modified: Long? = null):Serializable
