package com.edushare.artbookpro.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.edushare.artbookpro.R
import com.edushare.artbookpro.database.Art
import com.edushare.artbookpro.getOrAwaitValue
import com.edushare.artbookpro.launchFragmentInHiltContainer
import com.edushare.artbookpro.repo.FakeArtRepositoryTest
import com.edushare.artbookpro.viewmodel.ArtViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ArtDetailsFragmentTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testNavigationFromArtDetailsToImageAPI() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.onView(ViewMatchers.withId(R.id.artImageView)).perform(ViewActions.click())

        Mockito.verify(navController).navigate(
            ArtDetailFragmentDirections.actionArtDetailFragmentToImageApiFragment()
        )
    }

    @Test
    fun testOnBackPressed() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ) {
            Navigation.setViewNavController(requireView(), navController)
        }

        Espresso.pressBack()

        Mockito.verify(navController).popBackStack()

    }

    @Test
    fun testSave(){
        val testViewModel = ArtViewModel(FakeArtRepositoryTest())

        launchFragmentInHiltContainer<ArtDetailFragment>(
            factory = fragmentFactory
        ){
            viewModel = testViewModel
        }

        Espresso.onView(withId(R.id.nameText)).perform(replaceText("Mona Lisa"))
        Espresso.onView(withId(R.id.artistText)).perform(replaceText("Da Vinci"))
        Espresso.onView(withId(R.id.yearText)).perform(replaceText("1500"))
        Espresso.onView(withId(R.id.saveButton)).perform(click())

        assertThat(testViewModel.artList.getOrAwaitValue()).contains(
            Art("Mona Lisa", "Da Vinci", 1500,"")
        )
    }
}