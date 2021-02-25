package com.sandy.advancekotlinbase

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sandy.advancekotlinbase.databinding.ActivityMainBinding
import com.sandy.advancekotlinbase.di.AppComponent
import com.sandy.advancekotlinbase.di.DaggerAppComponent
import com.sandy.advancekotlinbase.di.NetworkModule
import com.sandy.advancekotlinbase.models.NewsRequestModel
import com.sandy.advancekotlinbase.network.NewsApiService
import com.sandy.advancekotlinbase.ui.NewsViewModel
import com.sandy.advancekotlinbase.ui.ViewModelFactory
import com.sandy.advancekotlinbase.utility.Result
import com.sandy.advancekotlinbase.utility.hasNetwork
import com.sandy.advancekotlinbase.utility.showLongToast
import com.sandy.advancekotlinbase.utility.showShortToast
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

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

        viewModel = ViewModelProviders.of(this, ViewModelFactory(newsApiService))
            .get(NewsViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            showShortToast("$errorMessage")
        })

        if (hasNetwork()!!) {
            val newsRequestModel =
                NewsRequestModel("in", "sports", getString(R.string.news_api_key))
            viewModel.getTopHeadlines(newsRequestModel).observe(this, Observer {
                it?.let { resource ->
                    when (resource.result) {
                        Result.SUCCESS -> {
                            viewModel.onRetrieveFinish()
                            resource.data?.let { newsMainModel ->
                                viewModel.onRetrieveSuccess(
                                    newsMainModel
                                )
                            }
                        }
                        Result.ERROR -> {
                            viewModel.onRetrieveFinish()
                            viewModel.onRetrieveError()
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
