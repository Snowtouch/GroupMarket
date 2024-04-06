package com.snowtouch.groupmarket

import android.app.Application
import com.snowtouch.account_feature.di.accountModule
import com.snowtouch.auth_feature.di.authModule
import com.snowtouch.core.di.commonRepositoryModule
import com.snowtouch.core.di.firebaseModule
import com.snowtouch.core.di.servicesModule
import com.snowtouch.core.di.snackbarModule
import com.snowtouch.feature_advertisement_details.di.adDetailsModule
import com.snowtouch.feature_groups.di.groupsModule
import com.snowtouch.feature_messages.di.messagesModule
import com.snowtouch.feature_new_advertisement.di.newAdModule
import com.snowtouch.groupmarket.di.appModule
import com.snowtouch.home_feature.di.homeModule
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
                appModule,
                servicesModule,
                commonRepositoryModule,
                authModule,
                firebaseModule,
                snackbarModule,
                accountModule,
                adDetailsModule,
                newAdModule,
                messagesModule,
                homeModule,
                groupsModule,
            )
        }
    }
}