package com.example.vk_currency_converter.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vk_currency_converter.domain.CurrencyInteractor
import com.example.vk_currency_converter.domain.model.ExchangeRates
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode

class MainViewModel(private val currencyInteractor: CurrencyInteractor) : ViewModel() {

    private var _converterState = MutableLiveData<ConverterState>(ConverterState.Loading)
    val listCurrency: LiveData<ConverterState> = _converterState

    private var _convertedValue = MutableLiveData<String>()
    val convertedValue: LiveData<String> = _convertedValue

    private var exchangeRates: HashMap<String, BigDecimal>? = null

    init {
        getExchangeRates()
    }

    fun converter(currencyFrom: String?, currencyTo: String?, amount: String) {
        updateExchangeRates()
        val valueCurrencyFrom: BigDecimal? = exchangeRates?.get(currencyFrom)
        val valueCurrencyTo: BigDecimal? = exchangeRates?.get(currencyTo)

        if (valueCurrencyFrom != null && valueCurrencyTo != null) {
            val res =
                (valueCurrencyTo * amount.toBigDecimal().setScale(2) / valueCurrencyFrom).setScale(
                    2,
                    RoundingMode.CEILING
                )
            _convertedValue.postValue(res.toString())
        } else {
            _converterState.postValue(ConverterState.ErrorServer)
        }
    }

    fun getExchangeRates() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = currencyInteractor.getExchangeRates()
            if (result.rates != null) {
                exchangeRates = result.rates
                val currencyList: MutableList<String> = mutableListOf()
                result.rates.map { currencyList.add(it.key) }

                _converterState.postValue(ConverterState.Content(currencyList.sorted()))
            } else {
                when (result.error) {
                    ExchangeRates.ExchangeRatesError.NO_INTERNET -> _converterState.postValue(
                        ConverterState.ErrorNoInternet
                    )

                    else ->
                        _converterState.postValue(ConverterState.ErrorServer)
                }
            }
        }
    }

    private fun updateExchangeRates() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = currencyInteractor.getExchangeRates()
            if (result.rates != null) {
                exchangeRates = result.rates
                val currencyList: MutableList<String> = mutableListOf()
                result.rates.map { currencyList.add(it.key) }
            } else {
                when (result.error) {
                    ExchangeRates.ExchangeRatesError.NO_INTERNET -> _converterState.postValue(
                        ConverterState.ErrorNoInternet
                    )

                    else ->
                        _converterState.postValue(ConverterState.ErrorServer)
                }
            }
        }
    }
}
