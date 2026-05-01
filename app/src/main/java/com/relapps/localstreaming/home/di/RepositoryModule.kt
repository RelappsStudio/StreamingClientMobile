package com.relapps.localstreaming.home.di

import com.relapps.localstreaming.auth.domain.repository.AuthRepository
import com.relapps.localstreaming.home.data.FakeMovieRepositoryImpl
import com.relapps.localstreaming.home.data.ProdMovieRepositoryImpl
import com.relapps.localstreaming.home.domain.MovieRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    //TODO: enable prod movie fetching once backend is ready
//    @Binds
//    @Singleton
//    abstract fun bindProdMovieRepository(
//        prodMovieRepositoryImpl: ProdMovieRepositoryImpl
//    ) : MovieRepository

    @Binds
    @Singleton
    abstract fun bindFakeMovieRepository(
        fakeMovieRepositoryImpl: FakeMovieRepositoryImpl,
    ) : MovieRepository



}