package com.assignment.country.model.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.assignment.country.helper.DatabaseConstants

@Entity(tableName = DatabaseConstants.TABLE_NAME_COUNTRY)
data class CountryEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Int,

    @ColumnInfo(name = "title")
    var title: String?,

    @ColumnInfo(name = "rows")
    var rows: ArrayList<RowEntity>
)