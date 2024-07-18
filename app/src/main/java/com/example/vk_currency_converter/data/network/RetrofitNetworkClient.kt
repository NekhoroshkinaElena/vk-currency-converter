package com.example.vk_currency_converter.data.network

import android.content.Context
import com.example.vk_currency_converter.data.NetworkClient
import com.example.vk_currency_converter.data.dto.RESULT_CODE_NO_INTERNET
import com.example.vk_currency_converter.data.dto.RESULT_CODE_SERVER_ERROR
import com.example.vk_currency_converter.data.dto.RESULT_CODE_SUCCESS
import com.example.vk_currency_converter.data.dto.Response
import com.example.vk_currency_converter.utils.isInternetAvailable
import retrofit2.HttpException

class RetrofitNetworkClient(
    private val exchangerRateService: ExchangerRateService,
    private val context: Context
) : NetworkClient {

    override suspend fun doRequest(): Response {
        if (!isInternetAvailable(context)) {
            return Response().apply { resultCode = RESULT_CODE_NO_INTERNET }
        }

        try {
            val response =
                exchangerRateService.getExchangeRates().apply { resultCode = RESULT_CODE_SUCCESS }
            return response
        } catch (e: HttpException) {
            println("RetrofitClient error code: ${e.code()} message: ${e.message}")
            return Response().apply { resultCode = RESULT_CODE_SERVER_ERROR }
        }
    }
}
