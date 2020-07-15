package com.assignment.country.di

import com.assignment.country.BuildConfig
import com.assignment.country.model.remote.AboutCountryService
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Class to create connection of Retrofit
 */
object ApiFactory {
    fun create(): AboutCountryService {
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit.create(AboutCountryService::class.java)
    }
}