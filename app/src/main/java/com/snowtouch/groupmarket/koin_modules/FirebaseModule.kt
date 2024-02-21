package com.snowtouch.groupmarket.koin_modules

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.snowtouch.groupmarket.R
import org.koin.dsl.module

const val isFirebaseLocal = true

val firebaseModule = module {
    single {
        val firebaseDatabaseLocalAddress = get<Application>().getString(R.string.firebaseDatabaseLocalPhone)
        val firebaseDatabaseRemoteAddress = get<Application>().getString(R.string.firebaseDatabaseRemote)

        if (isFirebaseLocal) {
            FirebaseDatabase.getInstance(firebaseDatabaseLocalAddress)
        } else {
            FirebaseDatabase.getInstance(firebaseDatabaseRemoteAddress)
        }
    }
    single {
        val auth = FirebaseAuth.getInstance()
        if (isFirebaseLocal) {
            //auth.useEmulator("10.0.2.2", 9099)
            auth.useEmulator("192.168.0.11", 9099)
        }
        auth
    }
    single {
        val firebaseStorageLocalAddress = get<Application>().getString(R.string.firebaseStorageLocalPhone)
        if (isFirebaseLocal) {
            FirebaseStorage.getInstance(firebaseStorageLocalAddress)
        } else {
            FirebaseStorage.getInstance()
        }
    }
}