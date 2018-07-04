package com.example.razli.weatherappsb.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.presenter.MainPresenter
import com.example.razli.weatherappsb.util.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


class MainActivity : AppCompatActivity(), MainContract.View{

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, this)

        button.setOnClickListener { addFavouritePlace() }

        // Fetch JSON
        fetchJson()

        // Set-up RecyclerView
        recyclerViewPlaces.layoutManager = LinearLayoutManager(this)
        recyclerViewPlaces.adapter = MainAdapter(presenter.getFavouritePlaces())
    }

    private fun addFavouritePlace() {

        // Get string from EditText and pass to presenter
        presenter.addFavouritePlace(editText.text.toString())
    }

    private fun fetchJson() {
        println("Fetching Json!")

        val url = "https://samples.openweathermap.org/data/2.5/weather?q=London&appid=b1b15e88fa797225412429c1c50c122a1"

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    // Create options menu in corner of toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.main_menu, menu)

        return super.onCreateOptionsMenu(menu)
    }
}
