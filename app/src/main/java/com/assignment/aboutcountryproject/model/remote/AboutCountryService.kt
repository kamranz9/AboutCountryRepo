package com.assignment.aboutcountryproject.model.remote

import com.assignment.aboutcountryproject.model.data.CountryEntity
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * Class which will write the API and URLs and make service call.
 */
interface AboutCountryService {
    @GET
    fun fetchCountryData(@Url url: String?): Single<CountryEntity>?

}