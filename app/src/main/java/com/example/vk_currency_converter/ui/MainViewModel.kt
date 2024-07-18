package com.example.vk_currency_converter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_currency_converter.domain.CurrencyInteractor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val currencyInteractor: CurrencyInteractor) : ViewModel() {

    private var _converterState = MutableLiveData<ConverterState>(ConverterState.Loading)
    val listCurrency: LiveData<ConverterState> = _converterState

    private var exchangeRates: HashMap<String, Double>? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = currencyInteractor.getExchangeRates()
            if (result.rates != null) {
                exchangeRates = result.rates
                val currencyList: MutableList<String> = mutableListOf()
                result.rates.map { currencyList.add(it.key) }

                _converterState.postValue(ConverterState.Content(currencyList))
            }
        }
    }

    fun converter(currencyFrom: String?, currencyTo: String?, amount: Double): Double {
        val valueCurrencyFrom: Double? = exchangeRates?.get(currencyFrom)
        val valueCurrencyTo: Double? = exchangeRates?.get(currencyTo)
        if (valueCurrencyFrom != null && valueCurrencyTo != null) {
            return valueCurrencyTo * amount / valueCurrencyFrom
        }
        return 0.0
    }
}
