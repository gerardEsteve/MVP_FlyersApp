package com.example.esteveshopfullytest.analytics

class FlyerSessionEventImpl(flyer_id : String, session_duration : String, first_read : Boolean) : StreamFullyEvent {

    override val eventType = "flyer_open"
    override val attributes  =
        hashMapOf("flyer_id" to flyer_id,
            "session_duration" to session_duration,
            "first_read" to first_read
        )

}
