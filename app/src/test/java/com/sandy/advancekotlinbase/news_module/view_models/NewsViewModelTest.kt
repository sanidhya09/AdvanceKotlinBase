package com.sandy.advancekotlinbase.news_module.view_models

import com.sandy.advancekotlinbase.news_module.models.NewsMainModel
import com.sandy.advancekotlinbase.news_module.models.request_models.NewsRequestModel
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


@RunWith(
    MockitoJUnitRunner::class
)
class NewsViewModelTest {
    @Mock
    lateinit var viewModel: NewsViewModel

    @Mock
    private lateinit var newsRequestModel: NewsRequestModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
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

    @Mock
    lateinit var newsMainModel: NewsMainModel

    @Test
    fun testOnRetrieveSuccess() {

        viewModel.onRetrieveSuccess(newsMainModel)
        Mockito.verify(viewModel)?.onRetrieveSuccess(newsMainModel)
    }

    @Test
    fun testGetTopHeadlines() {

        viewModel.getTopHeadlines(newsRequestModel)
        Mockito.verify(viewModel)?.getTopHeadlines(newsRequestModel)
    }

    @Test
    fun testObserverHeadlines() {

        viewModel.observeSrpLiveData
        Mockito.verify(viewModel)?.observeSrpLiveData
    }
}