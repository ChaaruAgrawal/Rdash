package com.assignment.rdash.di

import android.content.Context
import com.assignment.rdash.data.RDashAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://n8n.rdash.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideBookShelfApiService(retrofit: Retrofit): RDashAPI {
        return retrofit.create(RDashAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideApplicationContextUtil(@ApplicationContext appContext: Context): Context {
        return appContext
    }

}