package com.assignment.aboutcountryproject.model.local.dao


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.assignment.aboutcountryproject.helper.DatabaseConstants
import com.assignment.aboutcountryproject.model.data.CountryEntity
import io.reactivex.Single


@Dao
interface CountryDetailsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun setCountryDetails(countryEntity: CountryEntity?)

    @Query("SELECT * from "+ DatabaseConstants.TABLE_NAME_COUNTRY)
    fun getCountryDetailsList() : Single<CountryEntity>

    @Query("SELECT COUNT(*) FROM "+ DatabaseConstants.TABLE_NAME_COUNTRY)
    fun getRowCount(): LiveData<Int?>?

}