package com.example.esteveshopfullytest.model

import com.example.esteveshopfullytest.model.network.RetrofitAdapter
import com.example.esteveshopfullytest.presenters.FlyerPresenterInterface

// clase del model para obtener los datos de los flyers

class FlyerRepository (val flyerPresenterInterface: FlyerPresenterInterface) {

    suspend fun obtainFlyersData(){
        try {
            val response = RetrofitAdapter.apiClient.getAllFlyers()
            // Check if response was successful
            if (response.isSuccessful && response.body() != null) {
                // Retrieve data.
                val data = response.body()!!
                data.data?.let { flyerPresenterInterface.sendFlyers(it) }

                /*data.message?.let {
                    // Load URL into the ImageView using Coil.
                    iv_dog_image.load(it)
                }*/

            } else {
                // Show API error.
                // This is when the server responded with an error.
                flyerPresenterInterface.sendError("Else Error Occurred: ${response.message()}")
            }
        } catch (e: Exception) {
            // Show API error. This is the error raised by the client.
            // The API probably wasn't called in this case, so better check before assuming.
            flyerPresenterInterface.sendError("Catch Error Occurred: ${e.message}")
        }
    }

}