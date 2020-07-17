package com.assignment.country.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.assignment.country.R
import com.assignment.country.databinding.ActivityAboutCanadaBinding
import com.assignment.country.viewmodel.AboutCanadaViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class AboutCanadaActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityAboutCanadaBinding
    private val mViewModel: AboutCanadaViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_about_canada)
        mBinding.vm = mViewModel
    }
}
