package com.prasad.abbreviationsfinder.di.local

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.prasad.abbreviationsfinder.db.AppDatabase
import com.prasad.abbreviationsfinder.db.Converters
import com.prasad.abbreviationsfinder.utils.GsonParser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Singleton
    @Provides
    fun provideAppDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "app_db.db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .allowMainThreadQueries()
            .build()
    }
}