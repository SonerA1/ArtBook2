package com.soneralci.artbookhilttesting.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.soneralci.artbookhilttesting.R
import com.soneralci.artbookhilttesting.adapter.ImageRecyclerAdapter
import com.soneralci.artbookhilttesting.getOrAwaitValue
import com.soneralci.artbookhilttesting.launchFragmentInHiltContainer
import com.soneralci.artbookhilttesting.repo.FakeArtReposityoryTest
import com.soneralci.artbookhilttesting.viewmodel.ArtViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import javax.inject.Inject

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ImageAPIFragmentTest {
     @get:Rule
     var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ArtFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }
    @Test
    fun selectImage(){
        val navController = Mockito.mock(NavController::class.java)
        val selectedImageUrl = "soneralci.com"
        val testViewMode = ArtViewModel(FakeArtReposityoryTest())

        launchFragmentInHiltContainer<ImageAPIFragment>(factory = fragmentFactory){
            Navigation.setViewNavController(requireView(),navController)
            viewModel = testViewMode
            imageRecyclerAdapter.images = listOf(selectedImageUrl)
        }

        Espresso.onView(ViewMatchers.withId(R.id.imageRecyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageRecyclerAdapter.ImageViewHolder>(0,click())
        )
        Mockito.verify(navController).popBackStack()

        assertThat(testViewMode.selectedImageUrl.getOrAwaitValue()).isEqualTo(selectedImageUrl)
    }


}