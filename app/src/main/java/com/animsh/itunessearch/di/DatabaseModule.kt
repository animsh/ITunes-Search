package com.animsh.itunessearch.di

import android.content.Context
import androidx.room.Room
import com.animsh.itunessearch.data.database.ITunesSearchDatabase
import com.animsh.itunessearch.utils.Constants.Companion.ITUNES_SEARCH_DATABASE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by animsh on 4/18/2021.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
    ) = Room.databaseBuilder(
        context,
        ITunesSearchDatabase::class.java,
        ITUNES_SEARCH_DATABASE
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: ITunesSearchDatabase) = database.iTunesSearchDao()
}