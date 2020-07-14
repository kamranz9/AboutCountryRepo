package com.assignment.aboutcountryproject.helper

import androidx.room.TypeConverter
import com.assignment.aboutcountryproject.model.data.RowEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class Converters {

    @TypeConverter
    fun fromString(value: String?): ArrayList<RowEntity?>? {
        val listType =
            object : TypeToken<ArrayList<RowEntity?>?>() {}.type
        return Gson().fromJson<ArrayList<RowEntity?>>(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<RowEntity?>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }
}