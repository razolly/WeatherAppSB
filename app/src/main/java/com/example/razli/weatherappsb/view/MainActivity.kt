package com.example.razli.weatherappsb.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.R.id.*
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.presenter.MainPresenter
import com.example.razli.weatherappsb.util.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

class MainActivity : AppCompatActivity(), MainContract.View{

    private lateinit var presenter: MainContract.Presenter
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, this)
        setPresenter(presenter)

        button.setOnClickListener { addFavouritePlace() }
        handler = Handler()

        // Swipe-down-to-Refresh callback
        swipe_container.setOnRefreshListener { refreshPlaceList() }

        // Refresh every 1 hour
        refreshEveryOneHour()

        recyclerViewPlaces.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    private fun refreshEveryOneHour() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() { refreshPlaceList() }
        }, 0, 3600000)
    }

    private fun refreshPlaceList() {
        runnable = Runnable {
            presenter.updateListOfPlaces()
            swipe_container.isRefreshing = false
        }

        handler.postDelayed(runnable, 2000)
    }

    private fun addFavouritePlace() {

        val aPlace = editText.text.toString()

        presenter.addFavouritePlace(aPlace)

    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun showFavouritePlaces(favouritePlaces: List<Place>) {

        // Set-up RecyclerView
        recyclerViewPlaces.adapter = MainAdapter(favouritePlaces, this)
    }
}
