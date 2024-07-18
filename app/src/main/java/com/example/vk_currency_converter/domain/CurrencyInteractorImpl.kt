package com.example.vk_currency_converter.domain

import com.example.vk_currency_converter.domain.model.ExchangeRates

class CurrencyInteractorImpl(private val currencyRepository: CurrencyRepository) :
    CurrencyInteractor {

    override suspend fun getExchangeRates(): ExchangeRates {
        return currencyRepository.getExchangeRates()
    }
}
