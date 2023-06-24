package com.soneralci.artbookhilttesting.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.soneralci.artbookhilttesting.model.ImageResponse
import com.soneralci.artbookhilttesting.roomdb.Art
import com.soneralci.artbookhilttesting.util.Resource

class FakeArtReposityory : ArtRepositoryInterface {

    private val arts = mutableListOf<Art>()
    private val artsLiveData = MutableLiveData<List<Art>>(arts)

    override suspend fun inserArt(art: Art) {
        arts.add(art)
        refreshData()
    }

    override suspend fun deleteArt(art: Art) {
        arts.remove(art)
        refreshData()
    }

    override fun getArt(): LiveData<List<Art>> {
        return artsLiveData
    }

    override suspend fun serachImage(imageString: String): Resource<ImageResponse> {
        return Resource.success(ImageResponse(listOf(),0,0))
    }

    private fun refreshData(){
        artsLiveData.postValue(arts)
    }
}