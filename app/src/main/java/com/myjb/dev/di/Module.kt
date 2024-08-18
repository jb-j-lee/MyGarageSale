package com.myjb.dev.di

import com.myjb.dev.model.Repository
import com.myjb.dev.model.RepositoryImp
import com.myjb.dev.model.remote.datasource.AladinRemoteDataSource
import com.myjb.dev.model.remote.datasource.DataSource
import com.myjb.dev.model.remote.datasource.Yes24RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AladinModule {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class AladinAnnotation

    @Singleton
    @AladinAnnotation
    @Provides
    fun provideAladinRemoteDataSource(): DataSource {
        return AladinRemoteDataSource
    }
}

@Module
@InstallIn(SingletonComponent::class)
object Yes24Module {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class Yes24Annotation

    @Singleton
    @Yes24Annotation
    @Provides
    fun provideYes24RemoteDataSource(): DataSource {
        return Yes24RemoteDataSource
    }
}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        @AladinModule.AladinAnnotation aladinRemoteDataSource: DataSource,
        @Yes24Module.Yes24Annotation yes24RemoteDataSource: DataSource,
    ): Repository {
        return RepositoryImp(
            aladinDataSource = aladinRemoteDataSource,
            yes24DataSource = yes24RemoteDataSource
        )
    }
}