package com.example.vk_currency_converter.root

import android.app.Application
import com.example.vk_currency_converter.di.dataModule
import com.example.vk_currency_converter.di.interactorModule
import com.example.vk_currency_converter.di.repositoryModule
import com.example.vk_currency_converter.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoin()
    }

    private fun initializeKoin() {
        startKoin {
            androidContext(this@App)
            modules(dataModule, repositoryModule, interactorModule, viewModelModule)
        }
    }
}
