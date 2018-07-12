package com.example.razli.weatherappsb.view

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.R.id.editText
import com.example.razli.weatherappsb.R.id.swipe_container
import com.example.razli.weatherappsb.contract.MainContract
import com.example.razli.weatherappsb.model.Place
import com.example.razli.weatherappsb.presenter.MainPresenter
import com.example.razli.weatherappsb.util.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class MainActivity : AppCompatActivity(), MainContract.View {

    private lateinit var presenter: MainContract.Presenter
    private lateinit var adapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, this)
        setPresenter(presenter)

        button.setOnClickListener { addFavouritePlace() }

        // Swipe-down-to-Refresh callback
        swipe_container.setOnRefreshListener { presenter.refreshPlaceList() }

        recyclerViewPlaces.layoutManager = LinearLayoutManager(this)
    }

    override fun onStart() {
        super.onStart()
        presenter.start()
    }

    override fun stopRefreshing() {
        swipe_container.isRefreshing = false
    }

    private fun addFavouritePlace() {

        val aPlace = editText.text.toString()

        presenter.addFavouritePlace(aPlace)
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    override fun showFavouritePlaces(favouritePlaces: List<Place>) {
        adapter = MainAdapter(favouritePlaces.toMutableList(), this)

        adapter.setOnItemClickListener(object: MainAdapter.OnItemClickListener {
            override fun onItemClick(itemView: View, position: Int) {
                val place = itemView.placeNameTextView.text.toString()
                showAlertDialog(place)
            }
        })
        recyclerViewPlaces.adapter = adapter
    }

    override fun showFavouritePlace(favouritePlace: Place) {
        if (this::adapter.isInitialized) {
            adapter.addFavouritePlace(favouritePlace)
        } else {
            adapter = MainAdapter(mutableListOf(favouritePlace), this)

            adapter.setOnItemClickListener(object: MainAdapter.OnItemClickListener {
                override fun onItemClick(itemView: View, position: Int) {
                    val place = itemView.placeNameTextView.text.toString()
                    showAlertDialog(place)
                }
            })
            recyclerViewPlaces.adapter = adapter
        }
    }

    override fun showAlertDialog(place: String) {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Delete")

        builder.setMessage("Would you like to delete this place?")

        builder.setPositiveButton("YES") { dialog, which ->
            Toast.makeText(applicationContext, "Yes selected", Toast.LENGTH_SHORT).show()
            presenter.removePlace(place)
        }

        builder.setNegativeButton("No") { dialog, which ->
            Toast.makeText(applicationContext, "Nope", Toast.LENGTH_SHORT).show()
        }

        val dialog: AlertDialog = builder.create()

        dialog.show()
    }

    override fun showError(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT)
    }
}
