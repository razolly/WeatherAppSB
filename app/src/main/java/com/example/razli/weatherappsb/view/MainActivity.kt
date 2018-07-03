package com.example.razli.weatherappsb.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.presenter.MainActivityPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var mPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Create the presenter
        mPresenter = MainActivityPresenter(this)
    }

    override fun initView() {
        mButton.setOnClickListener { /* Nothing for now */ } // mPresenter.onClikc(view)
    }

    override fun setViewData(viewData: String) {

        // Set text of TextView
        mTextView.text = viewData
    }
}
