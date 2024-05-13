package com.genxsol.networkStatus.di

import android.content.Context
import com.genxsol.networkStatus.domain.NetworkRepository
import com.genxsol.networkStatus.data.repository.NetworkRepositoryImpl
import com.genxsol.networkStatus.domain.usecase.NetworkMonitorUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideNetworkHelper(
        @ApplicationContext context: Context
    ): NetworkRepository {
        return NetworkRepositoryImpl(context)
    }

    @Provides
    @Singleton
    fun provideNetworkStatusUseCase(
        networkRepository: NetworkRepository
    ): NetworkMonitorUseCase {
        return NetworkMonitorUseCase(networkRepository)
    }
}