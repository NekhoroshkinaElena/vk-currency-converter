package com.example.vk_currency_converter.ui

sealed class ConverterState {
    data object Loading : ConverterState()
    data class Content(val currency: List<String>) : ConverterState()
    data object ErrorServer : ConverterState()
    data object ErrorNoInternet : ConverterState()
}
