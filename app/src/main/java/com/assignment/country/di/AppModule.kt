package com.assignment.country.di

import com.assignment.country.BuildConfig
import com.assignment.country.model.local.AppDatabase
import com.assignment.country.model.remote.AboutCountryService
import com.assignment.country.model.repository.AboutCanadaRepository
import com.assignment.country.viewmodel.AboutCanadaViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { AboutCanadaViewModel(get(), androidContext()) }

}


val repoModule = module {

    factory { AboutCanadaRepository(get(), get()) }

}

val remoteModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<AboutCountryService> { get<Retrofit>().create(AboutCountryService::class.java) }
}


val localModule = module {

    single { AppDatabase.getDatabase(androidApplication()) }

    single { get<AppDatabase>().countryDetailsDao() }
}


val appModule = listOf(viewModelModule, repoModule, remoteModule, localModule)