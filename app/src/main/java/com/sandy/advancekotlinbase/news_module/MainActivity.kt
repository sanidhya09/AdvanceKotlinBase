package com.sandy.advancekotlinbase.news_module

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandy.advancekotlinbase.R
import com.sandy.advancekotlinbase.base_classes.BaseActivity
import com.sandy.advancekotlinbase.databinding.ActivityMainBinding
import com.sandy.advancekotlinbase.di.AppComponent
import com.sandy.advancekotlinbase.di.DaggerAppComponent
import com.sandy.advancekotlinbase.di.NetworkModule
import com.sandy.advancekotlinbase.news_module.models.request_models.NewsRequestModel
import com.sandy.advancekotlinbase.news_module.network.NewsApiService
import com.sandy.advancekotlinbase.news_module.network.NewsRemoteDataRepository
import com.sandy.advancekotlinbase.news_module.view_models.NewsViewModel
import com.sandy.advancekotlinbase.news_module.view_models.ViewModelFactory
import com.sandy.advancekotlinbase.utility.Result
import com.sandy.advancekotlinbase.utility.hasNetwork
import com.sandy.advancekotlinbase.utility.showLongToast
import com.sandy.advancekotlinbase.utility.showShortToast
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var newsApiService: NewsApiService
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: NewsViewModel

    private val component: AppComponent by lazy {
        DaggerAppComponent
            .builder()
            .networkModule(NetworkModule)
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.newsList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.newsList.setHasFixedSize(true)

        component.inject(this)

        viewModel =
            ViewModelProviders.of(this, ViewModelFactory(NewsRemoteDataRepository(newsApiService)))
                .get(NewsViewModel::class.java)

        viewModel.errorMessage.observe(this, { errorMessage ->
            showShortToast(errorMessage)
        })

        if (hasNetwork()!!) {
            val newsRequestModel =
                NewsRequestModel("in", "sports", getString(R.string.news_api_key))
            viewModel.getTopHeadlines(newsRequestModel).observe(this, {
                it?.let { apiState ->
                    when (apiState.result) {
                        Result.SUCCESS -> {
                            viewModel.onRetrieveFinish()
                            apiState.data?.let { newsMainModel ->
                                viewModel.onRetrieveSuccess(newsMainModel)
                            }
                        }
                        Result.ERROR -> {
                            viewModel.onRetrieveFinish()
                            viewModel.onRetrieveError(getString(R.string.api_news_error))
                        }
                        Result.LOADING -> {
                            viewModel.onRetrieveStart()
                        }
                    }
                }

            })
        } else {
            viewModel.onRetrieveFinish()
            showLongToast("Please check your network connection!!")
        }

        binding.viewModel = viewModel
    }


}
