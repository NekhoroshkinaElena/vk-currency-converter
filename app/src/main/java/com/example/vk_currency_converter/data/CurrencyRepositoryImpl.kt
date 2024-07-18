package com.example.vk_currency_converter.data

import com.example.vk_currency_converter.data.dto.RESULT_CODE_NO_INTERNET
import com.example.vk_currency_converter.data.dto.RESULT_CODE_SUCCESS
import com.example.vk_currency_converter.data.dto.ResponseExchangeRates
import com.example.vk_currency_converter.data.dto.responseToExchangeRates
import com.example.vk_currency_converter.domain.CurrencyRepository
import com.example.vk_currency_converter.domain.model.ExchangeRates

class CurrencyRepositoryImpl(private val networkClient: NetworkClient) : CurrencyRepository {

    override suspend fun getExchangeRates(): ExchangeRates {
        val response = networkClient.doRequest()
        when (response.resultCode) {
            RESULT_CODE_SUCCESS -> {
                return responseToExchangeRates(response as ResponseExchangeRates, null)
            }

            RESULT_CODE_NO_INTERNET ->
                return responseToExchangeRates(
                    null,
                    ExchangeRates.ExchangeRatesError.NO_INTERNET
                )

            else ->
                return responseToExchangeRates(
                    null,
                    ExchangeRates.ExchangeRatesError.SERVER_ERROR
                )
        }
    }
}
