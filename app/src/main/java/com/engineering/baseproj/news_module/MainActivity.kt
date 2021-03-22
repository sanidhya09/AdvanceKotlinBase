package com.engineering.baseproj.news_module

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.engineering.baseproj.R
import com.engineering.baseproj.base_classes.BaseActivity
import com.engineering.baseproj.databinding.ActivityMainBinding
import com.engineering.baseproj.di.AppComponent
import com.engineering.baseproj.di.DaggerAppComponent
import com.engineering.baseproj.di.NetworkModule
import com.engineering.baseproj.news_module.models.request_models.NewsRequestModel
import com.engineering.baseproj.news_module.network.NewsApiService
import com.engineering.baseproj.news_module.network.NewsRemoteDataRepository
import com.engineering.baseproj.news_module.view_models.NewsViewModel
import com.engineering.baseproj.news_module.view_models.ViewModelFactory
import com.engineering.baseproj.utility.Result
import com.engineering.baseproj.utility.hasNetwork
import com.engineering.baseproj.utility.showLongToast
import com.engineering.baseproj.utility.showShortToast
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
            viewModel.getTopHeadlines(newsRequestModel)
            viewModel.observeSrpLiveData.observe(this, {
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
