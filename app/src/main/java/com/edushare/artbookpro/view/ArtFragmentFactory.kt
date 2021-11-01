package com.edushare.artbookpro.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.bumptech.glide.RequestManager
import com.edushare.artbookpro.adapter.ArtRecyclerAdapter
import com.edushare.artbookpro.adapter.ImageRecyclerAdapter
import javax.inject.Inject

class ArtFragmentFactory @Inject constructor(
    private val artRecyclerAdapter: ArtRecyclerAdapter,
    private val glide:RequestManager,
    private val imageRecyclerAdapter: ImageRecyclerAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ArtDetailFragment::class.java.name -> ArtDetailFragment(glide)
            ArtFragment::class.java.name -> ArtFragment(artRecyclerAdapter)
            ImageApiFragment::class.java.name -> ImageApiFragment(imageRecyclerAdapter)
            else ->  super.instantiate(classLoader, className)
        }
    }
}