package com.example.bnet.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SessionData (@SerializedName("status") var status:Int,
                        @SerializedName("data") var data:MySession) : Serializable

data class MySession (@SerializedName("session") var session:String): Serializable