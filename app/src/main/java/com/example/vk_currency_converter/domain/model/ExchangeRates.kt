package com.example.vk_currency_converter.domain.model

import java.math.BigDecimal

data class ExchangeRates(val rates: HashMap<String, BigDecimal>?, val error: ExchangeRatesError?) {

    enum class ExchangeRatesError {
        NO_INTERNET,
        SERVER_ERROR,
    }
}
