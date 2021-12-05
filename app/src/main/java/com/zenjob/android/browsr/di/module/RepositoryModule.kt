package com.zenjob.android.browsr.di.module

import com.zenjob.android.browsr.network.MovieRepository
import com.zenjob.android.browsr.network.MovieRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun movieRepository(movieRepositoryImpl: MovieRepositoryImpl): MovieRepository
}