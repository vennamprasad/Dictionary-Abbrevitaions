package com.prasad.abbreviationsfinder.di

import com.prasad.abbreviationsfinder.repository.AbbreviationRepository
import com.prasad.abbreviationsfinder.repository.DictionaryRepository
import com.prasad.abbreviationsfinder.retrofit.api.AbbreviationApiInterface
import com.prasad.abbreviationsfinder.retrofit.api.DictionaryApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AbbreviationNetworkModule {

    @Singleton
    @Provides
    fun provideAcronymServiceApi(): AbbreviationApiInterface {
        return AbbreviationApiInterface.getInstance()
    }
}

@InstallIn(SingletonComponent::class)
@Module
class AcronymRepositoryModule {

    @Singleton
    @Provides
    fun provideAcronymRepository(abbreviationApiInterface: AbbreviationApiInterface): AbbreviationRepository {
        return AbbreviationRepository(abbreviationApiInterface)
    }
}


