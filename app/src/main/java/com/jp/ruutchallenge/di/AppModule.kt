package com.jp.ruutchallenge.di

import android.content.Context
import androidx.room.Room
import com.jp.ruutchallenge.data.room.UserRepository
import com.jp.ruutchallenge.data.room.dao.UserDao
import com.jp.ruutchallenge.data.room.db.UserDataBase
import com.jp.ruutchallenge.service.AccountService
import com.jp.ruutchallenge.service.impl.AccountServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAccountService(): AccountService = AccountServiceImpl()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): UserDataBase {
        return Room.databaseBuilder(
            appContext,
            UserDataBase::class.java,
            "user_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(appDatabase: UserDataBase) : UserDao{
        return appDatabase.userDao()
    }
    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: UserDao): UserRepository {
        return UserRepository(taskDao)
    }
}