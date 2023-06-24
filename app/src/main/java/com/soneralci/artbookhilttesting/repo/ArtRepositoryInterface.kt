package com.soneralci.artbookhilttesting.repo


import androidx.lifecycle.LiveData
import com.soneralci.artbookhilttesting.model.ImageResponse
import com.soneralci.artbookhilttesting.roomdb.Art
import com.soneralci.artbookhilttesting.util.Resource

interface ArtRepositoryInterface {

    suspend fun inserArt(art: Art)

    suspend fun deleteArt(art : Art)

    fun getArt() : LiveData<List<Art>>

    suspend fun serachImage(imageString : String) : Resource<ImageResponse>
}