package com.example.vk_currency_converter.domain

import com.example.vk_currency_converter.domain.model.ExchangeRates

interface CurrencyInteractor {

    suspend fun getExchangeRates(): ExchangeRates
}
