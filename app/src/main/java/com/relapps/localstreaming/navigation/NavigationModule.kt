package com.relapps.localstreaming.navigation

import com.relapps.localstreaming.FlutterNavigationPlugin
import dagger.Module
import dagger.Provides
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideNavigationManager(): NavManager = NavManager

    @EntryPoint
    @InstallIn(SingletonComponent::class)
    interface FlutterBridgeEntryPoint {
        fun getNavPlugin(): FlutterNavigationPlugin
    }
}