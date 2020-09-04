package com.weightwatchers.ww_exercise_02

import android.app.Application

class ContactsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        contactsApplicationContext = this
    }

    companion object {
        lateinit var contactsApplicationContext: ContactsApplication
    }
}