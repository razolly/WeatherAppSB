package com.example.razli.weatherappsb.forecast

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import com.bumptech.glide.Glide
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.FullForecast
import kotlinx.android.synthetic.main.fragment_page.*
import kotlinx.android.synthetic.main.fragment_page.view.*
import kotlinx.android.synthetic.main.place_list_item.*
import kotlinx.android.synthetic.main.place_list_item.view.*

class ForecastFragment : Fragment(), ForecastContract.View {

    private var pageNo: Int = 0

    private lateinit var wfPresenter: ForecastContract.Presenter
    //private lateinit var place: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageNo = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)

        wfPresenter = ForecastPresenter(this)
        setPresenter(wfPresenter)

        val forecastObj: FullForecast? = arguments?.getParcelable(FORECAST_KEY)
        val pageNo = arguments?.get(ARG_PAGE)
        println("From fragment, managed to get data!: $forecastObj")

        // todo fill up views with info. See the MainAdapter of the RecyclerView
        view.frag_temperatureTextView.text = "Temperature: " + forecastObj!!.forecastList[0].weatherDetail.temperature.toString() + "\u00b0" + "c"
        view.frag_timeTextView.text = "Time: ${forecastObj!!.forecastList[0].date}"

        val url = "http://openweathermap.org/img/w/" + forecastObj!!.forecastList[0].weatherIcon.first().icon + ".png"
        Glide.with(this).load(url).into(view.frag_imageView)

        // todo add recyclerview adapter here

        return view
    }

    companion object {
        const val ARG_PAGE = "ARG_PAGE"
        const val FORECAST_KEY = "FORECAST_KEY"

        fun newInstance(page: Int, forecastObj: FullForecast): ForecastFragment {

            val bundle = Bundle()
            bundle.putInt(ARG_PAGE, page)
            bundle.putParcelable(FORECAST_KEY, forecastObj)

            val fragment = ForecastFragment()
            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onStart() {
        super.onStart()
        wfPresenter.start()
    }

    override fun setPresenter(presenter: ForecastContract.Presenter) {
        wfPresenter = presenter
    }
}