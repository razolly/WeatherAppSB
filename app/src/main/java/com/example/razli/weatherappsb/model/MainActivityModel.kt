package com.example.razli.weatherappsb.model

import com.example.razli.weatherappsb.contract.MainContract

class MainActivityModel : MainContract.Model {

    override fun getData(): String {
        return "Hello MVP!"
    }
}