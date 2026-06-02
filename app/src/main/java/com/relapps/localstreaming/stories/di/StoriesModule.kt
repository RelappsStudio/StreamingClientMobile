package com.relapps.localstreaming.stories.di

import com.relapps.localstreaming.common.di.StoriesRetrofit
import com.relapps.localstreaming.stories.data.StoriesApiService
import com.relapps.localstreaming.stories.data.StoriesRepositoryImpl
import com.relapps.localstreaming.stories.domain.StoriesRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoriesModule {

    @Binds
    @Singleton
    abstract fun bindStoriesRepository(storiesRepositoryImpl: StoriesRepositoryImpl) : StoriesRepository

    companion object {
        @Provides
        @Singleton
        fun provideStoriesApiService(@StoriesRetrofit retrofit: Retrofit) : StoriesApiService {
            return retrofit.create(StoriesApiService::class.java)
        }
    }
}