package com.erbeandroid.fate.core.common.di

import com.erbeandroid.fate.core.common.dispatcher.Dispatcher
import com.erbeandroid.fate.core.common.dispatcher.FateDispatcher.Default
import com.erbeandroid.fate.core.common.dispatcher.FateDispatcher.IO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Dispatcher(IO)
    fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(Default)
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default
}