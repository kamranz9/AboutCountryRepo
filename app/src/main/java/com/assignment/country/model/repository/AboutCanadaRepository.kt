package com.assignment.country.model.repository

import com.assignment.country.helper.Constants
import com.assignment.country.model.local.dao.CountryDetailsDao
import com.assignment.country.model.remote.AboutCountryService

class AboutCanadaRepository constructor(
    private val remote: AboutCountryService,
    private val local: CountryDetailsDao
) {

    fun getCountryDetailsListLocal() =
        local.getCountryDetailsListLocal().doOnSuccess { getCountryDetailsListRemote() }
            .onErrorResumeNext(getCountryDetailsListRemote())

    private fun getCountryDetailsListRemote() = remote.fetchCountryData(Constants.RANDOM_USER_URL)
        ?.doOnSuccess { local.setCountryDetails(it) }

    fun getRowCount() = local.getRowCount()

}