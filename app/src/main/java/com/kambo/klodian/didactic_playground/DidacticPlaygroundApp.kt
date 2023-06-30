package com.kambo.klodian.didactic_playground

import android.app.Application

/**
 * [Application] is a class that:
 * 1. is created when the application is started
 * 2. is destroyed when the application is closed
 * 3. has one only and only one instance
 * 4. will be present in memory during all the app execution
 *
 * It's used for one time operations such as:
 * 1. one time configuration or initialization of components or library.
 * 2. provide the dependency tree based on your D.I. framework.
 */
class DidacticPlaygroundApp: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}