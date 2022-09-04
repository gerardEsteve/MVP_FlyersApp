package com.example.esteveshopfullytest.model.network

import androidx.annotation.Keep
import com.example.esteveshopfullytest.model.FlyerModel

@Keep
class ResponseFlyerModel {
    var metadata : FlyerMetadata? = null
    var data : Array<FlyerModel>? = null
}

class FlyerMetadata {
    var success : Int? = null
    var code : Int? = null
    var message : String? = null
    var time : Double? = null
}
