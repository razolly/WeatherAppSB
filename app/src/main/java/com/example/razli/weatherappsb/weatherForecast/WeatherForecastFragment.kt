package com.example.razli.weatherappsb.weatherForecast

import android.widget.TextView
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import com.example.razli.weatherappsb.R

class WeatherForecastFragment : Fragment() {

    private var mPage: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mPage = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)
        val textView = view as TextView
        textView.text = "Fragment #$mPage"
        return view
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): WeatherForecastFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)
            val fragment = WeatherForecastFragment()
            fragment.setArguments(args)
            return fragment
        }
    }
}