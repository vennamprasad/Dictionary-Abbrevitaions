package com.prasad.abbreviationsfinder.di.network

import com.prasad.abbreviationsfinder.repository.DictionaryRepository
import com.prasad.abbreviationsfinder.api.network.DictionaryApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class DictionaryNetworkModule {
    @Singleton
    @Provides
    fun provideDictionaryServiceApi(): DictionaryApiInterface {
        return DictionaryApiInterface.getInstance()
    }
}

@InstallIn(SingletonComponent::class)
@Module
class DictionaryRepositoryModule {
    @Singleton
    @Provides
    fun provideDictionaryRepository(dictionaryApiInterface: DictionaryApiInterface): DictionaryRepository {
        return DictionaryRepository(dictionaryApiInterface)
    }
}