package com.assignment.country.model.repository

import com.assignment.country.helper.Constants
import com.assignment.country.model.local.dao.CountryDetailsDao
import com.assignment.country.model.remote.AboutCountryService

class AboutCanadaRepository constructor(
    private val remote: AboutCountryService,
    private val local: CountryDetailsDao
) {

    fun getCountryDetailsListRemote() = remote.fetchCountryData(Constants.RANDOM_USER_URL)
        ?.doOnNext { local.setCountryDetails(it) }

    fun getCountryDetailsListLocal() = local.getCountryDetailsListLocal()

    fun getRowCount() = local.getRowCount()


}