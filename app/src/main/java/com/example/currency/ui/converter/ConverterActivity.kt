package com.example.currency.ui.converter

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currency.R
import com.example.currency.data.models.Countries
import com.example.currency.data.network.IApiCaller.callApiGetRates
import com.example.currency.util.constants.AppConstants.FROM_TAG
import com.example.currency.util.extensions.getCountriesAsStringArray
import com.example.currency.util.sqlite.CountriesSQLiteManager
import kotlinx.android.synthetic.main.activity_converter.*


class ConverterActivity : AppCompatActivity() {

    var countriesList: Countries? = null
    private var countriesStringList: ArrayList<String>? = null
    var fromCountry: String? = null
    var toCountry: String? = null
    var toCountrySymbol: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_converter)
        setup()
    }

    private fun setup() {
        getCountries()
        setOnConvertClicked()
        observeResponse()
    }

    private fun getCountries() {
        countriesList = CountriesSQLiteManager.getSQLiteData()
        countriesStringList = getCountriesAsStringArray(countriesList)
        if (!countriesStringList.isNullOrEmpty()) {
            val sortedList = countriesStringList!!.sortedBy { it }
            setupSpinner(sortedList, fromSpinner)
            setupSpinner(sortedList, toSpinner)
        }
    }

    private fun setupSpinner(countriesStringList: List<String>, spinner: Spinner) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, countriesStringList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
        setSpinnerItemSelectedListener(spinner)
    }

    private fun setSpinnerItemSelectedListener(spinner: Spinner) {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                var selectedCountry = ""
                var currencySymbol = ""
                val sortedList = countriesStringList!!.sortedBy { it }
                val selectedItem = sortedList[position]
                for (country in countriesList?.results!!.values) {
                    if (country.name == selectedItem) {
                        selectedCountry = country.currencyID.toString()
                        currencySymbol = country.currencySymbol.toString()
                    }
                }
                if (spinner.tag == FROM_TAG) {
                    fromCountry = selectedCountry
                } else {
                    toCountry = selectedCountry
                    toCountrySymbol = currencySymbol
                }
            }
        }
    }

    private fun setOnConvertClicked() {
        convertBtn.setOnClickListener {
            getCurrencyRates()
        }
    }

    private fun getCurrencyRates() {
        callApiGetRates(fromCountry.toString(), toCountry.toString(), _mutableLiveDataResponse)
    }

    private fun observeResponse() {
        mutableLiveDataResponse.observe(this, { rate ->
            val conversionValue = fromET.text.toString().toInt() * rate
            convertResultTV.text = "$conversionValue $toCountrySymbol"
        })
    }

    companion object {

        private var _mutableLiveDataResponse = MutableLiveData<Double>()
        val mutableLiveDataResponse: LiveData<Double>
            get() = _mutableLiveDataResponse

    }

}