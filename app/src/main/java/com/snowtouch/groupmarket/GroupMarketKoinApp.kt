package com.snowtouch.groupmarket

import android.app.Application
import com.snowtouch.core.di.firebaseModule
import com.snowtouch.groupmarket.koin_modules.serviceModule
import com.snowtouch.core.di.snackbarModule
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
                com.snowtouch.auth_feature.di.authModule,
                com.snowtouch.account_feature.di.accountModule,
                com.snowtouch.home_feature.di.homeModule,
                com.snowtouch.feature_groups.di.groupsModule,
                snackbarModule,
                serviceModule,
                viewModelModule
            )
        }
    }
}