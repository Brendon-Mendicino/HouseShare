package com.houseshare.di

import android.app.Application
import com.houseshare.data.navigation.NavigationRepositoryImpl
import com.houseshare.domain.navigation.NavigationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNavigationRepository(application: Application): NavigationRepository {
        return NavigationRepositoryImpl(
            application
        )
    }
}