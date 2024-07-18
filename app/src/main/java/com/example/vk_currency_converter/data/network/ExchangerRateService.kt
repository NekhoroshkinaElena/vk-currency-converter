package com.example.vk_currency_converter.data.network

import com.example.vk_currency_converter.data.dto.ResponseExchangeRates
import retrofit2.http.GET

interface ExchangerRateService {

    @GET("latest.json?app_id=f56b36e34de940a8a658edc5f3d31842")
    suspend fun getExchangeRates(): ResponseExchangeRates
}
