package com.example.esteveshopfullytest.model

import androidx.annotation.WorkerThread
import com.example.esteveshopfullytest.BaseApplication

import com.example.esteveshopfullytest.model.network.RetrofitAdapter
import com.example.esteveshopfullytest.presenters.FlyerPresenterInterface
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class FlyerRepository (val flyerPresenterInterface: FlyerPresenterInterface) {

    val flyerDAO = BaseApplication.instance.database.flyerDao()


    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(flyer: Flyer) {
        flyerDAO.insertFlyer(flyer)
    }

    suspend fun getAllFlyers(): List<Flyer>{
        return flyerDAO.getAllFlyers()
    }

    suspend fun obtainFlyersData(){

        try {
            val response = RetrofitAdapter.apiClient.getAllFlyers()
            // Check if response was successful
            if (response.isSuccessful && response.body() != null) {
                // Retrieve data.
                val data = response.body()!!
                val empty = getAllFlyers().isEmpty()
                data.data?.let {
                    // convert from flyerModel to Flyer
                    var newFlyersArray : ArrayList<Flyer> = ArrayList()
                    val initialUrl:String = "https://it-it-media.shopfully.cloud/images/volantini/"
                    val finalUrl:String = "@3x.jpg"

                    for (i in it.indices){

                        val builder = StringBuilder()
                        builder.append(initialUrl)
                            .append(it[i].id)
                            .append(finalUrl)

                        var flyer: Flyer = Flyer(it[i],false,builder.toString())
                        if(empty) Picasso.get().load(builder.toString()).fetch()

                        newFlyersArray.add(flyer)
                    }

                    if(empty) flyerDAO.insertAllFlyers(newFlyersArray)
                    returnData(newFlyersArray)
                }

            } else {
                // This is when the server responded with an error.
                    getFromBD()
            }
        } catch (e: Exception) {
            // API error. This is the error raised by the client.
            // The API probably wasn't called in this case, so better check before assuming.
                getFromBD()

        }
    }

    suspend fun returnData(flyers: ArrayList<Flyer>) {
        return withContext(Dispatchers.Main) {
            flyerPresenterInterface.sendFlyers(flyers)
        }
    }

    suspend fun getFromBD()  {
        val flyersArrayList = ArrayList(getAllFlyers())
        returnData(flyersArrayList)
    }

}