package com.example.razli.weatherappsb.weatherForecast

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.Place

class WeatherForecastFragment : Fragment() {

    private var mPage: Int = 0

    private lateinit var place: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)

        // todo fill up views with info. See the MainAdapter of the RecyclerView
//        val textView = view as TextView
//        textView.text = "Fragment #$mPage"
        return view
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): WeatherForecastFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = WeatherForecastFragment()
            fragment.arguments = args
            return fragment
        }
    }
}