package com.example.bnet.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class UserNoteData(val data: MutableList<MutableList<UserNote>>)

@Entity
data class UserNote(
    @PrimaryKey
    @SerializedName("id") var id: String,
    @SerializedName("body") var text: String,
    @SerializedName("da") var created: Long? = null,
    @SerializedName("dm") var modified: Long? = null):Serializable

data class UserNoteId(@SerializedName("id") var id: String): Serializable
