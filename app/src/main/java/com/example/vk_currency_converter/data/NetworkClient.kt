package com.example.vk_currency_converter.data

import com.example.vk_currency_converter.data.dto.Response

interface NetworkClient {

    suspend fun doRequest(): Response
}
