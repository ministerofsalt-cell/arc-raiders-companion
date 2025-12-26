package com.arcraiders.companion

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ArcRaidersApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize anything you need here
    }
    
    companion object {
        // Easy access to application context
        lateinit var instance: ArcRaidersApplication
            private set
    }
    
    init {
        instance = this
    }
    
    val context: Context
        get() = applicationContext
}
