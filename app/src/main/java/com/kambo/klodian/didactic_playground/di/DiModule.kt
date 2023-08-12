package com.kambo.klodian.didactic_playground.di

import android.content.Context
import com.kambo.klodian.didactic_playground.data.Repo
import com.kambo.klodian.didactic_playground.data.RepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class Main

@Qualifier
annotation class MainImmediate


@Module
@InstallIn(SingletonComponent::class)
class DiModule {

    @Provides
    fun provideApplicationContext(@ApplicationContext appContext: Context): Context = appContext

    @Provides
    fun provideRepo(impl: RepoImpl): Repo = impl


    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher  = Dispatchers.IO


    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher  = Dispatchers.Default


    @Main
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher  = Dispatchers.Main


    @MainImmediate
    @Provides
    fun provideMainImmediateDispatcher() : CoroutineDispatcher = Dispatchers.Main.immediate
}