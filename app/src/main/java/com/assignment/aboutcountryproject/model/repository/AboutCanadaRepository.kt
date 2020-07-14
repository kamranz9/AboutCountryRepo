package com.assignment.aboutcountryproject.model.repository

import com.assignment.aboutcountryproject.helper.Constants
import com.assignment.aboutcountryproject.model.local.dao.CountryDetailsDao
import com.assignment.aboutcountryproject.model.remote.AboutCountryService

class AboutCanadaRepository constructor(private val remote:AboutCountryService, private val local :CountryDetailsDao) {


    /*private var countryDetailsDao: CountryDetailsDao?

    init {
        val db = AppDatabase.getDatabase(application)
        countryDetailsDao = db?.countryDetailsDao()
    }

    fun getCountryDetailsList() = countryDetailsDao?.getCountryDetailsList()

    fun getRowCount() = countryDetailsDao?.getRowCount()


    fun setInsertCountryData(countryEntity: CountryEntity) {
        launch { insertData(countryEntity) }
    }

    private suspend fun insertData(entity: CountryEntity) {
        withContext(Dispatchers.IO) {
            countryDetailsDao?.setCountryDetails(entity)
        }
    }


*/

    fun getCountryDetailsList()= local.getCountryDetailsList()
        .onErrorResumeNext {
            remote.fetchCountryData(Constants.RANDOM_USER_URL)
                ?.doOnSuccess {
                    local.setCountryDetails(it)
                }
        }
}