package com.example.esteveshopfullytest.model

data class Flyer(val id: String, val retailer_id: String, val title: String, var read: Boolean, val imgURL: String) {

    constructor(flyerModel: FlyerModel,read: Boolean, imgURL: String ) : this(flyerModel.id,flyerModel.retailer_id,flyerModel.title,read,imgURL) {

    }

}