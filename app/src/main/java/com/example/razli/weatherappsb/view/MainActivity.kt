package com.example.razli.weatherappsb.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
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

class MainActivity : AppCompatActivity(), MainContract.View{

    private lateinit var presenter: MainContract.Presenter
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, this)

        button.setOnClickListener { addFavouritePlace() }
        handler = Handler()

        // Swipe-down-to-Refresh callback
        swipe_container.setOnRefreshListener { refreshPlaceList() }

        recyclerViewPlaces.layoutManager = LinearLayoutManager(this)
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

        // Add to HashSet in presenter. It will be saved in SharedPreferences
        // A new Place object will be created and added to List in Presenter. Wont be saved when app is killed
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
