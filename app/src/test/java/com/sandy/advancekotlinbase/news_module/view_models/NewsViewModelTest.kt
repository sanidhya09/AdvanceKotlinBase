package com.sandy.advancekotlinbase.news_module.view_models

import com.sandy.advancekotlinbase.news_module.models.request_models.NewsRequestModel
import com.sandy.advancekotlinbase.news_module.network.NewsApiService
import com.sandy.advancekotlinbase.news_module.network.NewsRemoteDataRepository
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class NewsViewModelTest {
    @Mock
    lateinit var viewModel: NewsViewModel

    @Mock
    lateinit var newsApiService: NewsApiService

    @Mock
    lateinit var newsRemoteDataRepository: NewsRemoteDataRepository

    private lateinit var newsRequestModel: NewsRequestModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        newsRequestModel = NewsRequestModel("in", "sports", "")

    }

    @After
    fun tearDown() {
    }

    @Test
    fun testOnRetrieveError() {
        val s = "newsRequestModel"
        viewModel.onRetrieveError(s)
        Mockito.verify(viewModel)?.onRetrieveError(s)
    }
}