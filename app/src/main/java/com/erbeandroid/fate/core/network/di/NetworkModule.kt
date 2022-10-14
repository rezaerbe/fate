package com.erbeandroid.fate.core.network.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.erbeandroid.fate.core.network.datasource.FateRemoteDataSource
import com.erbeandroid.fate.core.network.datasource.RemoteDataSource
import com.erbeandroid.fate.core.network.service.FateService
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NetworkModule {

    @Binds
    fun bindRemoteDataSource(
        fateRemoteDataSource: FateRemoteDataSource
    ): RemoteDataSource

    companion object {
        @Provides
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        fun provideChuckCollector(
            @ApplicationContext context: Context
        ): ChuckerCollector {
            return ChuckerCollector(
                context,
                true,
                RetentionManager.Period.ONE_HOUR
            )
        }

        @Provides
        fun provideChuckInterceptor(
            @ApplicationContext context: Context,
            chuckCollector: ChuckerCollector
        ): ChuckerInterceptor {
            return ChuckerInterceptor.Builder(context)
                .collector(chuckCollector)
                .maxContentLength(250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()
        }

        @Provides
        fun provideOkHttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            chuckInterceptor: ChuckerInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(chuckInterceptor)
                .build()
        }

        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit.Builder {
            return Retrofit.Builder()
                .baseUrl("https://fategrandorder.fandom.com/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create())
        }

        @Provides
        @Singleton
        fun provideFateService(builder: Retrofit.Builder): FateService {
            return builder
                .build()
                .create(FateService::class.java)
        }
    }
}