package com.relapps.localstreaming.common.di

import com.relapps.localstreaming.common.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider() : DispatcherProvider {
        return DispatcherProvider(
            main = Dispatchers.Main,
            io = Dispatchers.IO,
            default = Dispatchers.Default
        )
    }
}