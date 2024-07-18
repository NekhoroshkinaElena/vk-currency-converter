package com.example.vk_currency_converter.data.dto

const val RESULT_CODE_NO_INTERNET = -1
const val RESULT_CODE_SUCCESS = 200
const val RESULT_CODE_SERVER_ERROR = 500

open class Response {
    var resultCode = 0
}
