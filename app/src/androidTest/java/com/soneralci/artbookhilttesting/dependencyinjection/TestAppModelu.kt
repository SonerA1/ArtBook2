package com.soneralci.artbookhilttesting.dependencyinjection

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import androidx.room.Room
import com.soneralci.artbookhilttesting.roomdb.ArtDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestAppModelu {

    @Provides
    @Named("testDatabase")
    fun injectInMemoryRoom(@ApplicationContext context : Context) =
        Room.inMemoryDatabaseBuilder(context,ArtDatabase::class.java)
            .allowMainThreadQueries()
            .build()


}