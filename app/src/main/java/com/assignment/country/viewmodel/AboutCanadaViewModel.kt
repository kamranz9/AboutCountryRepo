package com.assignment.country.viewmodel

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.country.helper.async
import com.assignment.country.model.data.CountryEntity
import com.assignment.country.model.data.RowEntity
import com.assignment.country.model.repository.AboutCanadaRepository
import io.reactivex.Single

class AboutCanadaViewModel constructor(private val repo: AboutCanadaRepository) : ViewModel() {

    //////////////////data//////////////
    private val loading = ObservableBoolean(false)
    val title = ObservableField<String>()
    var userRecycler: ObservableInt? = ObservableInt(View.GONE)
    var progressBar: ObservableInt? = ObservableInt(View.GONE)
    val isLoading: ObservableBoolean = ObservableBoolean(false)
    var userLabel: ObservableInt?  = ObservableInt(View.VISIBLE)
    var messageLabel: ObservableField<String>? = ObservableField("")


    //////////////////binding//////////////
    fun getCountryDetailsList(): Single<CountryEntity> =
        repo.getCountryDetailsList()
            .async(1000)
            .doOnSuccess { t: CountryEntity? ->
                t?.let {
                    title.set(it.title)
                    val removedNullList = removedNullList(it.rows)
                    _data.postValue(removedNullList)
                    isLoading.set(false)

                }
            }
            .doOnSubscribe { startLoad() }
            .doAfterTerminate { stopLoad() }


    /**
     * This method would add only rows which are not null
     *
     * @param rowList List passed after reponse
     * @return Null removed List
     */
    private fun removedNullList(rowList: List<RowEntity>): List<RowEntity>? {
        val removed: MutableList<RowEntity> = arrayListOf()
        for (i in rowList.indices) if (checkIfRowIsNull(rowList[i])) removed += rowList[i]
        return removed
    }


    /**
     * This method will check if Condition matches that all items to be displayed are null
     *
     * @param row Model would be passed to check condition
     * @return Would return true if all items are null
     */
    private fun checkIfRowIsNull(row: RowEntity): Boolean {
        return row.title != null || row.description != null || row.imageHref != null
    }


    private fun startLoad() {
        loading.set(true)
        progressBar!!.set(View.VISIBLE)
    }

    private fun stopLoad() {
        progressBar!!.set(View.GONE)
        loading.set(false)
        isLoading.set(false)
    }

    private val _data = MutableLiveData<List<RowEntity>>()
    val data: LiveData<List<RowEntity>>
        get() = _data

}