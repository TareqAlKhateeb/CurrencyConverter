package com.example.currency.data.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Countries : Serializable {

    @SerializedName("results")
    @Expose
    val results: Map<String, Result>? = null

}

class Result : Serializable {

    @SerializedName("alpha3")
    @Expose
    val alpha3: String? = null

    @SerializedName("currencyId")
    @Expose
    val currencyID: String? = null

    @SerializedName("currencyName")
    @Expose
    val currencyName: String? = null

    @SerializedName("currencySymbol")
    @Expose
    val currencySymbol: String? = null

    @SerializedName("id")
    @Expose
    val id: String? = null

    @SerializedName("name")
    @Expose
    val name: String? = null
}