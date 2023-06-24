package com.soneralci.artbookhilttesting.dependencyinjection

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.soneralci.artbookhilttesting.R
import com.soneralci.artbookhilttesting.api.RetrofitAPI
import com.soneralci.artbookhilttesting.repo.ArtRepository
import com.soneralci.artbookhilttesting.repo.ArtRepositoryInterface
import com.soneralci.artbookhilttesting.roomdb.ArtDao
import com.soneralci.artbookhilttesting.roomdb.ArtDatabase
import com.soneralci.artbookhilttesting.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// after ArtBookApplication create this object Module class
//FOR HILT
@Module // take this
@InstallIn(SingletonComponent::class) // and take this.
object AppModule {

    //RoomDB
    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context : Context) = Room.databaseBuilder(
        context,ArtDatabase::class.java,"ArtBookDB"
        ).build()

    //Dao
    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    //RetrofitAPI
    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitAPI::class.java)
    }


    @Singleton
    @Provides
    fun injectNormalRepo(dao : ArtDao, api:RetrofitAPI) = ArtRepository(dao,api) as ArtRepositoryInterface



    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context : Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )



}