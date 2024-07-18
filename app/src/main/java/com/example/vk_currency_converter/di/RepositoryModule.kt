package com.example.vk_currency_converter.di

import com.example.vk_currency_converter.data.CurrencyRepositoryImpl
import com.example.vk_currency_converter.domain.CurrencyRepository
import org.koin.dsl.module

val repositoryModule = module {

    single<CurrencyRepository> {
        CurrencyRepositoryImpl(networkClient = get())
    }
}
