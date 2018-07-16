package com.example.razli.weatherappsb.forecast

import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.app.Fragment
import android.view.View
import com.example.razli.weatherappsb.R

class ForecastFragment : Fragment(), ForecastContract.View {

    private var mPage: Int = 0

    private lateinit var wfPresenter: ForecastContract.Presenter
    //private lateinit var place: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mPage = arguments!!.getInt(ARG_PAGE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_page, container, false)

        wfPresenter = ForecastPresenter(this)
        setPresenter(wfPresenter)

        // todo fill up views with info. See the MainAdapter of the RecyclerView
//        val textView = view as TextView
//        textView.text = "Fragment #$mPage"
        return view
    }

    companion object {
        val ARG_PAGE = "ARG_PAGE"

        fun newInstance(page: Int): ForecastFragment {
            val args = Bundle()
            args.putInt(ARG_PAGE, page)

            val fragment = ForecastFragment()
            fragment.arguments = args

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

    override fun onDestroyView() {
        super.onDestroyView()
    }
}