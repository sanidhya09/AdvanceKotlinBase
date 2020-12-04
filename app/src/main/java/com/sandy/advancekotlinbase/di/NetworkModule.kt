package com.sandy.advancekotlinbase.di

import com.sandy.advancekotlinbase.network.NewsApi
import com.sandy.advancekotlinbase.utility.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


@Module
object NetworkModule {

    @Provides
    internal fun provideNewsApi(retrofit: Retrofit): NewsApi = retrofit.create(NewsApi::class.java)

    @Provides
    internal fun provideRetrofitInterface(): Retrofit {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor)
            .addInterceptor(Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.header("Token", "TempAndroid")
                return@Interceptor chain.proceed(builder.build())
            })
            .connectTimeout(30000, TimeUnit.SECONDS)
            .readTimeout(2000, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
    }
}