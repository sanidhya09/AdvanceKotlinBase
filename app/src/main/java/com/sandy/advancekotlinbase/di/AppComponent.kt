package com.sandy.advancekotlinbase.di

import com.sandy.advancekotlinbase.MainActivity
import com.sandy.advancekotlinbase.news_module.view_models.NewsViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(newsViewModel: NewsViewModel)
    fun inject(mainActivity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): AppComponent
        fun networkModule(networkModule: NetworkModule): Builder
    }
}