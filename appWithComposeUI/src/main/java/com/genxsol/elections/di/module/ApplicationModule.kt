package com.genxsol.elections.di.module

import android.app.Application
import androidx.room.Room
import com.genxsol.elections.api.ResultsService
import com.genxsol.elections.api.ResultsServiceFactory
import com.genxsol.elections.common.Const
import com.genxsol.elections.common.dispatcher.DefaultDispatcherProvider
import com.genxsol.elections.common.dispatcher.DispatcherProvider
import com.genxsol.elections.common.logger.AppLogger
import com.genxsol.elections.common.logger.Logger
import com.genxsol.elections.data.database.DatabaseService
import com.genxsol.elections.data.database.ElectionDatabase
import com.genxsol.elections.data.database.ElectionDatabaseService
import com.genxsol.elections.domain.model.ElectionsRepository
import com.genxsol.elections.data.repository.ElectionsRepositoryImpl
import com.genxsol.elections.di.DbName
import com.genxsol.elections.domain.usecase.PollResultsWithCandidateNameUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideNetworkService(): ResultsService {
        return ResultsServiceFactory.buildResultsService()
    }

    @Provides
    @Singleton
    fun provideLogger(): Logger = AppLogger()

    @Provides
    @Singleton
    fun provideDispatcher(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun providePollResultsWithCandidateNameUseCase(
        electionsRepository: ElectionsRepository,
        dispatcherProvider: DispatcherProvider
    ): PollResultsWithCandidateNameUseCase =
        PollResultsWithCandidateNameUseCase(
        electionsRepository,
        dispatcherProvider
    )

    @Provides
    @Singleton
    fun provideElectionsRepository(
        resultsService: ResultsService,
        databaseService: DatabaseService
    ): ElectionsRepository =
        ElectionsRepositoryImpl(resultsService, databaseService)

    @DbName
    @Provides
    fun provideDbName(): String = Const.DB_NAME

    @Singleton
    @Provides
    fun provideElectionDatabase(
        application: Application,
        @DbName dbName: String
    ): ElectionDatabase {
        return Room.databaseBuilder(
            application,
            ElectionDatabase::class.java,
            dbName
        )
            .build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(electionDatabase: ElectionDatabase): DatabaseService {
        return ElectionDatabaseService(electionDatabase)
    }
}