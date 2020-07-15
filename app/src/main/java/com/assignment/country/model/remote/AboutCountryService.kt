package com.assignment.country.model.remote

import com.assignment.country.model.data.CountryEntity
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