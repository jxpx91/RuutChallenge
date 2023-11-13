package com.jp.ruutchallenge.toplevel

import android.app.Application
import androidx.room.Room
import com.jp.ruutchallenge.data.room.db.UserDataBase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {

    companion object {
        lateinit var database: UserDataBase
    }
    override fun onCreate() {
        super.onCreate()
        BaseApplication.database = Room.databaseBuilder(this, UserDataBase::class.java, "user-db").build()
    }
}