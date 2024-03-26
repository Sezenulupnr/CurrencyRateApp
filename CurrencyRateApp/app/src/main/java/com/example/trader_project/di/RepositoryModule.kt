package com.example.trader_project.di

import com.example.trader_project.repository.AuthRepository
import com.example.trader_project.repository.CurrencyRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCurrencyRepository(): CurrencyRepository {
        return CurrencyRepository()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ) = AuthRepository(firebaseAuth)
}