package com.assignment.aboutcountryproject.viewmodel

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.assignment.aboutcountryproject.helper.async
import com.assignment.aboutcountryproject.model.data.CountryEntity
import com.assignment.aboutcountryproject.model.data.RowEntity
import com.assignment.aboutcountryproject.model.repository.AboutCanadaRepository
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import io.reactivex.Single

class AboutCanadaViewModel constructor(private val repo: AboutCanadaRepository) : ViewModel(){

    //////////////////data//////////////
    private val loading= ObservableBoolean(false)
    val content = ObservableField<String>()
    val title = ObservableField<String>()
    val error = ObservableField<Throwable>()
    /*private val titleRow = ObservableField<String>()
    val description = ObservableField<String>()
    val imageHref = ObservableField<String>()*/
    //lateinit var rowEntity: List<RowEntity>
    //////////////////binding//////////////
    fun getCountryDetailsList(): Single<CountryEntity> =
        repo.getCountryDetailsList()
            .async(1000)
            .doOnSuccess { t: CountryEntity? ->
                t?.let {
                    title.set(it.title)

                    //rowEntity = it.rows
                    _data.postValue(it.rows)


/*
                    for ((index, value) in rowEntity.withIndex()){ //Tryin to get items in the list together with it's indez
                        titleRow.set(rowEntity.get(index).title)
                        description.set(rowEntity.get(index).description)
                        imageHref.set(rowEntity.get(index).imageHref)
                    }*/
                }
            }
            .doOnSubscribe { startLoad() }
            .doAfterTerminate { stopLoad() }



    private fun startLoad()=loading.set(true)
    private fun stopLoad()=loading.set(false)

    private val _data = MutableLiveData<List<RowEntity>>()
    val data: LiveData<List<RowEntity>>
        get() = _data


    @BindingAdapter(value = ["setAdapter"])
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }


    /*fun getProfileThumb(): String? {
        return if (user.getImageHref() != null) {
            user.getImageHref()
        } else {
            ""
        }
    }*/

}