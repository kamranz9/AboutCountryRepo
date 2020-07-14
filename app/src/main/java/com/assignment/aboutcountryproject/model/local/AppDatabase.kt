package com.assignment.aboutcountryproject.model.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.assignment.aboutcountryproject.helper.DatabaseConstants
import com.assignment.aboutcountryproject.helper.Converters
import com.assignment.aboutcountryproject.model.local.dao.CountryDetailsDao
import com.assignment.aboutcountryproject.model.data.CountryEntity


@Database(
    entities = [CountryEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun countryDetailsDao(): CountryDetailsDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(AppDatabase::class.java) {
                    if (INSTANCE == null) {

                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java, DatabaseConstants.DB_NAME
                        )
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}