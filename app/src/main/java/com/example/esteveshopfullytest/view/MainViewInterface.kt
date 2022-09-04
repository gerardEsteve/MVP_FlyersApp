package com.example.esteveshopfullytest.view

import com.example.esteveshopfullytest.model.Flyer

interface MainViewInterface {

    fun populateMainView(data : ArrayList<Flyer>)
    fun showToast(message: String)

    fun showLoading(show: Boolean)
    fun showFlyerDetails(position: Int)
    fun sendAnalyticsFlyerOpen(position: Int)
    fun setFlyerReaded(position: Int)
    fun showReadedFlyers(showReaded: Boolean)
}