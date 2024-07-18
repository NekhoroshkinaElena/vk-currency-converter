package com.example.vk_currency_converter.di

import com.example.vk_currency_converter.domain.CurrencyInteractor
import com.example.vk_currency_converter.domain.CurrencyInteractorImpl
import org.koin.dsl.module

val interactorModule = module {

    factory<CurrencyInteractor> {
        CurrencyInteractorImpl(get())
    }
}
