package com.example.vk_currency_converter.di

import com.example.vk_currency_converter.data.NetworkClient
import com.example.vk_currency_converter.data.network.ExchangerRateService
import com.example.vk_currency_converter.data.network.RetrofitNetworkClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<ExchangerRateService> {
        Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangerRateService::class.java)
    }

    single<NetworkClient> {
        RetrofitNetworkClient(
            exchangerRateService = get(),
            context = androidContext()
        )
    }
}
