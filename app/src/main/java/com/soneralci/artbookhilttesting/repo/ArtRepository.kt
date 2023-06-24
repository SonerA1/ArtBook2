package com.soneralci.artbookhilttesting.repo

import androidx.lifecycle.LiveData
import com.soneralci.artbookhilttesting.api.RetrofitAPI
import com.soneralci.artbookhilttesting.model.ImageResponse
import com.soneralci.artbookhilttesting.roomdb.Art
import com.soneralci.artbookhilttesting.roomdb.ArtDao
import com.soneralci.artbookhilttesting.util.Resource
import java.lang.Exception
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao, private val retrofitAPI: RetrofitAPI
) : ArtRepositoryInterface {
    override suspend fun inserArt(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getArt(): LiveData<List<Art>> {
        return artDao.observeArt()
    }

    override suspend fun serachImage(imageString: String): Resource<ImageResponse> {
        return try {
            val response = retrofitAPI.imageSearch(imageString)
            if (response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error",null)
            }else{
                Resource.error("Error",null)
            }
        }catch (e: Exception){
            Resource.error("No data!", null)
        }
    }
}