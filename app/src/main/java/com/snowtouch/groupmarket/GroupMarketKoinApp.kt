package com.snowtouch.groupmarket

import android.app.Application
import com.snowtouch.groupmarket.koin_modules.firebaseModule
import com.snowtouch.groupmarket.koin_modules.isFirebaseLocal
import com.snowtouch.groupmarket.koin_modules.snackbarModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class GroupMarketKoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (isFirebaseLocal) {
            startKoin {
                androidContext(this@GroupMarketKoinApp)
                modules(
                    firebaseModule,
                    snackbarModule
                )
            }
        }
    }
}