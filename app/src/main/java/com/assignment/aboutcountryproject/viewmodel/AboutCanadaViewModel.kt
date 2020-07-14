package com.assignment.aboutcountryproject.viewmodel

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.aboutcountryproject.helper.async
import com.assignment.aboutcountryproject.model.data.CountryEntity
import com.assignment.aboutcountryproject.model.data.RowEntity
import com.assignment.aboutcountryproject.model.repository.AboutCanadaRepository
import io.reactivex.Single

class AboutCanadaViewModel constructor(private val repo: AboutCanadaRepository) : ViewModel() {

    //////////////////data//////////////
    private val loading = ObservableBoolean(false)
    val content = ObservableField<String>()
    val title = ObservableField<String>()
    val error = ObservableField<Throwable>()
    var progressBar: ObservableInt? = ObservableInt(View.GONE)
    val isLoading: ObservableBoolean = ObservableBoolean(false)

    //////////////////binding//////////////
    fun getCountryDetailsList(): Single<CountryEntity> =
        repo.getCountryDetailsList()
            .async(1000)
            .doOnSuccess { t: CountryEntity? ->
                t?.let {
                    title.set(it.title)
                    _data.postValue(it.rows)
                    isLoading.set(false)

                }
            }
            .doOnSubscribe { startLoad() }
            .doAfterTerminate { stopLoad() }


    private fun startLoad() {
        loading.set(true)
        isLoading.set(true)
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