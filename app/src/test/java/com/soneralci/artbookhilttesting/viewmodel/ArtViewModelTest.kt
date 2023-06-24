package com.soneralci.artbookhilttesting.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.soneralci.artbookhilttesting.MainCoroutineRule
import com.soneralci.artbookhilttesting.getOrAwaitValueTest
import com.soneralci.artbookhilttesting.repo.FakeArtReposityory
import com.soneralci.artbookhilttesting.util.Status
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ArtViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ArtViewModel

    @Before
    fun setup(){
        //Test Doubles
        viewModel = ArtViewModel(FakeArtReposityory())
    }

    @Test
    fun `insert art without year returns error`(){
        viewModel.makeArt("Mona Lisa", "Davinci","")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun `insert art without name returns error`(){
        viewModel.makeArt("", "Davinci","1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun `insert art without artistName returns error`(){
        viewModel.makeArt("Mona Lisa", "","1800")
        val value = viewModel.insertArtMessage.getOrAwaitValueTest()
        assertThat(value.status).isEqualTo(Status.ERROR)
    }
}