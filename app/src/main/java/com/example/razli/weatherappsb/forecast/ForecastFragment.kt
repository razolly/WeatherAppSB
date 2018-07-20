package com.example.razli.weatherappsb.forecast

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.example.razli.weatherappsb.R
import com.example.razli.weatherappsb.model.FullForecast
import kotlinx.android.synthetic.main.fragment_list.*


class ForecastFragment : Fragment(), ForecastContract.View {

    private lateinit var adapter: ForecastListAdapter

    private var pageNo: Int = 0

    private lateinit var wfPresenter: ForecastContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pageNo = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_list, container, false)

        wfPresenter = ForecastPresenter(this)
        setPresenter(wfPresenter)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val forecastObj: FullForecast? = arguments?.getParcelable(FORECAST_KEY)

        adapter = ForecastListAdapter(forecastObj!!.forecastList, context!!)
        recycler_view_forecast.adapter = adapter
        recycler_view_forecast.layoutManager = LinearLayoutManager(context)
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