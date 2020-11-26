package com.example.currency.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class User : Serializable {

    @SerializedName("user_name")
    @Expose
    var userName: String? = null

    @SerializedName("password")
    @Expose
    var password: String? = null

}