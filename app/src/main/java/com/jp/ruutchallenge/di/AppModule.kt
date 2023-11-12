package com.jp.ruutchallenge.di

import com.jp.ruutchallenge.service.AccountService
import com.jp.ruutchallenge.service.impl.AccountServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideAccountService(): AccountService = AccountServiceImpl()
}