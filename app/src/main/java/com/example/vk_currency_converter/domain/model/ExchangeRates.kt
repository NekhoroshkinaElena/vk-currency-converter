package com.example.vk_currency_converter.domain.model

data class ExchangeRates(val rates: HashMap<String, Double>?, val error: ExchangeRatesError?) {

    enum class ExchangeRatesError {
        NO_INTERNET,
        SERVER_ERROR,
    }
}
