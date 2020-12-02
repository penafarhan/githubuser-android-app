package com.codeiva.githubuserapp

import android.app.Application
import android.content.Context

/**
 * @author farhan
 * created at at 11:41 on 24/06/2020.
 */
class App : Application() {

    companion object {
        private lateinit var instance: App

        fun getInstance(): App {
            return instance
        }

        fun getContext(): Context {
            return instance.applicationContext
        }
    }

    override fun onCreate() {
        instance = this
        super.onCreate()
    }
}