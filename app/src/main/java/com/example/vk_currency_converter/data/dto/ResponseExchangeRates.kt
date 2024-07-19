package com.example.vk_currency_converter.data.dto

import java.math.BigDecimal

data class ResponseExchangeRates(val rates: HashMap<String, BigDecimal>?) : Response()
