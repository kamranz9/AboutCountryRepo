package com.assignment.country.model.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.country.helper.DatabaseConstants
import com.assignment.country.model.data.CountryEntity
import io.reactivex.Single


@Dao
interface CountryDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setCountryDetails(countryEntity: CountryEntity?)

    @Query("SELECT * from "+ DatabaseConstants.TABLE_NAME_COUNTRY)
    fun getCountryDetailsListLocal() : Single<CountryEntity>

}