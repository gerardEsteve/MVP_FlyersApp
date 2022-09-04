package com.example.esteveshopfullytest.presenters

import com.example.esteveshopfullytest.model.Flyer
import com.example.esteveshopfullytest.model.FlyerModel

interface FlyerPresenterInterface {

    fun sendFlyers(array : Array<FlyerModel>)
    fun sendError(message: String)

}
