package com.demo.lottery.utils

import android.app.Application
import com.demo.lottery.helpers.PrefsHelper
import com.demo.lottery.helpers.Session
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

class LotteryApplication: Application() {


    val appModule = module {
        single {
            PrefsHelper(androidContext())
        }
        single {
            Session()
        }
    }
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@LotteryApplication)
            modules(appModule)
        }
    }
}