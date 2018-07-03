package com.example.razli.weatherappsb.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View{

    private lateinit var mPresenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mPresenter = MainPresenter(this)
    }

    fun addFavouritePlace(view: View) {

        // Get string from EditText and pass to presenter
        mPresenter.addFavouritePlace(mEditText.text.toString())
    }

    override fun showFavouritePlace(place: String) {
        mTextView.append(place)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        mPresenter = presenter
    }
}
