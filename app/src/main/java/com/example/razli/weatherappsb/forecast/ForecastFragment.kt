package com.example.razli.weatherappsb.forecast

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.FullForecast
import com.example.razli.weatherappsb.model.WeatherForecast

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

        // todo fill up views with info. See the MainAdapter of the RecyclerView

//        val forecastObj = arguments?.get("FORECAST_KEY")
        val forecastObj = arguments?.getBundle("FORECAST_KEY")
//        val forecastObj = savedInstanceState!!["FORECAST_KEY"]
        //val forecastObj = arguments?.getParcelable("FORECAST_KEY")
        println("From fragment, managed to get data!: $forecastObj")

        return view
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int, forecastObj: FullForecast): ForecastFragment {

            println("Fragment new instance: $forecastObj")

            val bundle = Bundle()
            bundle.putInt(ARG_PAGE, page)
            bundle.putParcelable("FORECAST_KEY", forecastObj)

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