package com.edushare.artbookpro.repo

import androidx.lifecycle.LiveData
import com.edushare.artbookpro.api.RetrofitApi
import com.edushare.artbookpro.database.Art
import com.edushare.artbookpro.database.ArtDao
import com.edushare.artbookpro.model.ImageResponse
import com.edushare.artbookpro.util.Resource
import javax.inject.Inject

class ArtRepository @Inject constructor(
    private val artDao: ArtDao,
    private val retrofitApi: RetrofitApi
) : ArtRepositoryInterface {
    override suspend fun insertAll(art: Art) {
        artDao.insertArt(art)
    }

    override suspend fun deleteArt(art: Art) {
        artDao.deleteArt(art)
    }

    override fun getAll(): LiveData<List<Art>> {
        return artDao.observeArts()
    }

    override suspend fun searchImage(imageString: String): Resource<ImageResponse> {
        return try {

            val response = retrofitApi.imageSearch(imageString)
            if (response.isSuccessful) {
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("Error", null)
            } else {
                Resource.error("Error", null)
            }
        } catch (e: Exception) {
            Resource.error("No data", null)
        }

    }
}