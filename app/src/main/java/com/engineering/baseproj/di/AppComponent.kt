package com.engineering.baseproj.di

import com.engineering.baseproj.news_module.MainActivity
import com.engineering.baseproj.news_module.view_models.NewsViewModel
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