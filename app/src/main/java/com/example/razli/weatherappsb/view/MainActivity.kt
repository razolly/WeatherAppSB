package com.example.razli.weatherappsb.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View{

    private lateinit var presenter: MainContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, this)

        button.setOnClickListener { addFavouritePlace() }
    }

    private fun addFavouritePlace() {

        // Get string from EditText and pass to presenter
        presenter.addFavouritePlace(editText.text.toString())
    }

    override fun showFavouritePlace(listOfPlaces: HashSet<String>) {

        // Clear text
        textView.text = ""

        for(place in listOfPlaces) {
            textView.append(place + "\n")
        }
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
