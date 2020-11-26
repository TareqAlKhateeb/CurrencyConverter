package com.example.currency.util.constants

object AppConstants {

    //Api keys
    const val API_KEY = "77f36f3f62e0ede92998"
    const val GET_COUNTRIES_URL = "https://free.currconv.com/api/v7/countries?apiKey=$API_KEY"
    const val CURRENCY_RATE_EXT_URL = "&compact=ultra&callback=sampleCallback&apiKey=$API_KEY"
    const val CURRENCY_RATE_URL = "https://free.currconv.com/api/v7/convert?q="

    //Shared preferences keys
    const val LOGGED_IN_USER_NAME = "logged_in_user_name"
    const val IS_USER_LOGGED_IN = "is_user_logged_in"
    const val NOTES = "notes"

    //Tags
    const val FROM_TAG = "from"

    //Note types
    const val EXPENSES = "Expenses"
    const val INCOME = "Income"

}