package com.example.currency.data.network

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.currency.R
import com.example.currency.data.models.Countries
import com.example.currency.ui.base.App
import com.example.currency.util.constants.AppConstants.CURRENCY_RATE_EXT_URL
import com.example.currency.util.constants.AppConstants.CURRENCY_RATE_URL
import com.example.currency.util.constants.AppConstants.GET_COUNTRIES_URL
import com.example.currency.util.extensions.parseResponse
import com.example.currency.util.extensions.showToast
import com.example.currency.util.sqlite.CountriesSQLiteManager.insertIntoSQLiteDB

object IApiCaller {

    private var queue: RequestQueue? = null

    fun initWith(con: Context) {

        queue = Volley.newRequestQueue(con)

    }

    fun callApiGetCountries() {

        val request = JsonObjectRequest(
            Request.Method.GET,
            GET_COUNTRIES_URL,
            null,
            { response ->

                val responseObject = parseResponse(
                    response.toString(),
                    Countries()
                ) as Countries

                insertIntoSQLiteDB(responseObject)

            },
            {

                App.applicationContext()?.getString(R.string.get_countries_error)?.let { it1 ->
                    showToast(it1)
                }

            })
        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.setShouldCache(false)
        queue?.add(request)

    }

    fun callApiGetRates(from: String, to: String, _mutableLiveDataResponse: MutableLiveData<Double>) {

        val url = CURRENCY_RATE_URL + from + "_" + to + CURRENCY_RATE_EXT_URL

        val request = StringRequest(
            url,
            { response ->

                val rate = response.toString().substringAfter(":").substringBefore("}")
                _mutableLiveDataResponse.value = rate.toDouble()

            },
            {

                App.applicationContext()?.getString(R.string.get_rates_error)?.let { it1 ->
                    showToast(it1)
                }

            })
        request.retryPolicy = DefaultRetryPolicy(
            30000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        request.setShouldCache(false)
        queue?.add(request)

    }

}