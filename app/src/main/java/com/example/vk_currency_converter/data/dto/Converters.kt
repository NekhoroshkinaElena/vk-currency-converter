package com.example.vk_currency_converter.data.dto

import com.example.vk_currency_converter.domain.model.ExchangeRates

fun responseToExchangeRates(
    response: ResponseExchangeRates?,
    error: ExchangeRates.ExchangeRatesError?
): ExchangeRates {
    return ExchangeRates(response?.rates, error)
}
