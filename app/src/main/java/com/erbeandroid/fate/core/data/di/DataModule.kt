package com.erbeandroid.fate.core.data.di

import com.erbeandroid.fate.core.data.repository.FateRepository
import com.erbeandroid.fate.core.data.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindRepository(
        fateRepository: FateRepository
    ): Repository
}