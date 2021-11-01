package com.edushare.artbookpro.repo

import androidx.lifecycle.LiveData
import com.edushare.artbookpro.database.Art
import com.edushare.artbookpro.model.ImageResponse
import com.edushare.artbookpro.util.Resource

interface ArtRepositoryInterface {

    suspend fun insertAll(art: Art)

    suspend fun deleteArt(art: Art)

    fun getAll() : LiveData<List<Art>>

    suspend fun searchImage(imageString : String) : Resource<ImageResponse>
}