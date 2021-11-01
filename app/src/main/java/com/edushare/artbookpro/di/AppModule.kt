package com.edushare.artbookpro.di

import android.content.Context
import androidx.room.Room
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.edushare.artbookpro.R
import com.edushare.artbookpro.api.RetrofitApi
import com.edushare.artbookpro.database.ArtDao
import com.edushare.artbookpro.database.ArtDatabase
import com.edushare.artbookpro.repo.ArtRepository
import com.edushare.artbookpro.repo.ArtRepositoryInterface
import com.edushare.artbookpro.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context, ArtDatabase::class.java, "ArtBookDB"
    ).build()

    @Singleton
    @Provides
    fun injectDao(database: ArtDatabase) = database.artDao()

    @Singleton
    @Provides
    fun injectRetrofitApi(): RetrofitApi {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(RetrofitApi::class.java)
    }

    @Singleton
    @Provides
    fun injectNormalRepo(dao:ArtDao,
                         api: RetrofitApi
    ) = ArtRepository(dao,api) as ArtRepositoryInterface

    @Singleton
    @Provides
    fun injectGlide(@ApplicationContext context: Context) = Glide.with(context)
        .setDefaultRequestOptions(
            RequestOptions().placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
        )
}