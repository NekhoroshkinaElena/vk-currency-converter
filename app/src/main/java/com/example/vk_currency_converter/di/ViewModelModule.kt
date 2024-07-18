package com.example.vk_currency_converter.di

import com.example.vk_currency_converter.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        MainViewModel(currencyInteractor = get())
    }
}
