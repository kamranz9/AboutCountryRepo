package com.assignment.aboutcountryproject.di


//import com.assignment.aboutcountryproject.BuildConfig
import com.assignment.aboutcountryproject.model.local.AppDatabase
import com.assignment.aboutcountryproject.model.local.dao.CountryDetailsDao
import com.assignment.aboutcountryproject.model.remote.AboutCountryService
import com.assignment.aboutcountryproject.model.repository.AboutCanadaRepository
import com.assignment.aboutcountryproject.viewmodel.AboutCanadaViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { AboutCanadaViewModel(get<AboutCanadaRepository>()) }
    //or use reflection
//    viewModel<PaoViewModel>()

}

val repoModule = module {

    factory  <AboutCanadaRepository> { AboutCanadaRepository(get(), get()) }

}

val remoteModule = module {

    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl("https://dl.dropboxusercontent.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<AboutCountryService> { get<Retrofit>().create(AboutCountryService::class.java) }
}


val localModule = module {

    single<AppDatabase> { AppDatabase.getDatabase(androidApplication()) }

    single<CountryDetailsDao> { get<AppDatabase>().countryDetailsDao() }
}


val appModule = listOf(viewModelModule, repoModule, remoteModule, localModule)