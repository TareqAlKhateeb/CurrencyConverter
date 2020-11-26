package com.example.currency.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Note: Serializable {

    @SerializedName("expenses")
    @Expose
    var expenses: String? = null

    @SerializedName("income")
    @Expose
    var income: String? = null
}