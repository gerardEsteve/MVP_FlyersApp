package com.example.esteveshopfullytest.analytics

class FlyerOpenEventImpl(retailer_id : String, flyer_id : String, title : String, position : Int, first_read : Boolean) : StreamFullyEvent {

    override val eventType = "flyer_open"
    override val attributes  =
        hashMapOf("retailer_id" to retailer_id,
            "flyer_id" to flyer_id,
            "title" to title,
            "position" to position,
            "first_read" to first_read
        )

}
