package com.snowtouch.groupmarket

import android.app.Application
import com.snowtouch.groupmarket.account.di.accountModule
import com.snowtouch.groupmarket.auth.di.authModule
import com.snowtouch.groupmarket.groups.di.groupsModule
import com.snowtouch.groupmarket.home.di.homeModule
import com.snowtouch.groupmarket.koin_modules.firebaseModule
import com.snowtouch.groupmarket.koin_modules.serviceModule
import com.snowtouch.groupmarket.koin_modules.snackbarModule
import com.snowtouch.groupmarket.koin_modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class GroupMarketKoinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@GroupMarketKoinApp)
            modules(
                firebaseModule,
                authModule,
                accountModule,
                homeModule,
                groupsModule,
                snackbarModule,
                serviceModule,
                viewModelModule
            )
        }
    }
}