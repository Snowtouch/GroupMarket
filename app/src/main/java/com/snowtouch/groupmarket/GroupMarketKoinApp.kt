package com.snowtouch.groupmarket

import android.app.Application
import com.snowtouch.groupmarket.koin_modules.firebaseModule
import com.snowtouch.groupmarket.koin_modules.serviceModule
import com.snowtouch.groupmarket.koin_modules.snackbarModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GroupMarketKoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@GroupMarketKoinApp)
            modules(
                firebaseModule,
                snackbarModule,
                serviceModule
            )
        }
    }
}