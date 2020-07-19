package com.assignment.country.viewmodel

import android.content.Context
import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment.country.R
import com.assignment.country.helper.InternetCheckHelper
import com.assignment.country.model.data.RowEntity
import com.assignment.country.model.repository.AboutCanadaRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


class AboutCanadaViewModel(
    private val repo: AboutCanadaRepository,
    androidContext: Context
) : ViewModel() {

    private val context = androidContext

    //////////////////data//////////////
    private val loading = ObservableBoolean(false)
    val title = ObservableField<String>()
    var userRecycler: ObservableInt? = ObservableInt(View.VISIBLE)
    var progressBar: ObservableInt = ObservableInt(View.GONE)
    val isLoading: ObservableBoolean = ObservableBoolean(false)
    var userLabel: ObservableInt? = ObservableInt(View.GONE)
    var messageLabel: ObservableField<String>? = ObservableField("")

    private val returnData by lazy {
        return@lazy callAPI()
    }

    fun callAPI(): Disposable? {
        startLoad()
        return repo.getCountryDetailsListLocal().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread()).subscribe({
                stopLoad()
                userRecycler?.set(View.VISIBLE)
                userLabel?.set(View.GONE)
                messageLabel?.set("")

                title.set(it.title)
                val removedNullList = removedNullList(it.rows)
                _data.postValue(removedNullList)

            }, {
                stopLoad()
                userLabel?.set(View.VISIBLE)
                userRecycler?.set(View.GONE)

                if (InternetCheckHelper.isInternetAvailable(context)) {

                    repo.getRowCount()?.observeForever {
                        if (it == null || it == 0) {
                            messageLabel?.set(context.getString(R.string.error_no_data_available))
                        }
                    }

                } else {
                    messageLabel?.set(context.getString(R.string.error_message_loading_internet))
                }

            })
    }

    init {
        returnData
    }

    /**
     * This method would add only rows which are not null
     *
     * @param rowList List passed after response
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
        progressBar.set(View.VISIBLE)
        loading.set(true)
    }

    private fun stopLoad() {
        isLoading.set(false)
        progressBar.set(View.GONE)
        loading.set(false)
    }

    private val _data = MutableLiveData<List<RowEntity>>()
    val data: LiveData<List<RowEntity>>
        get() = _data

}