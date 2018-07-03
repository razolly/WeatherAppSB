package com.example.razli.weatherappsb.util

interface BaseView<T : BasePresenter> {

    fun setPresenter(presenter: T)

}