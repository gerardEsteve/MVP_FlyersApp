package com.example.esteveshopfullytest.presenters

import com.example.esteveshopfullytest.model.Flyer
import com.example.esteveshopfullytest.model.FlyerModel
import com.example.esteveshopfullytest.model.FlyerRepository
import com.example.esteveshopfullytest.model.network.RetrofitAdapter
import com.example.esteveshopfullytest.view.MainViewInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope

import kotlinx.coroutines.launch



class FlyersListPresenter(val mainViewInterface: MainViewInterface) : CoroutineScope by MainScope(),FlyerPresenterInterface {

    //crear lista de flyers
    private var flyerRepository = FlyerRepository(this@FlyersListPresenter)

    fun initializeMainView(){

            // crear coroutina y pedirle al model que obtenga los datos
            // mostrar icono de Loading en la mainActivity
            // cuando el model tenga estos datos se comunicará con el presenter para que obtenga los datos

        launch(Dispatchers.Main) {
            showLoading(true)
            flyerRepository.obtainFlyersData()
        }

    }

    fun onItemClickedAtPosition(position: Int) {
        mainViewInterface.sendAnalyticsFlyerOpen(position)
        mainViewInterface.setFlyerReaded(position)
        mainViewInterface.showFlyerDetails(position)

        mainViewInterface.showToast("You CLicked $position")
    }

    fun toggleClicked(isChecked: Boolean){
        mainViewInterface.showReadedFlyers(isChecked)
    }

    fun showLoading(show:Boolean) {
        mainViewInterface.showLoading(show)
    }


    override fun sendFlyers(array : Array<FlyerModel>) {
        showLoading(false)
        // convert from flyerModel to Flyer
        var newFlyersArray : ArrayList<Flyer> = ArrayList()
        val initialUrl:String = "https://it-it-media.shopfully.cloud/images/volantini/"
        val finalUrl:String = "@3x.jpg"

        for (i in array.indices){

            val builder = StringBuilder()
            builder.append(initialUrl)
                .append(array[i].id)
                .append(finalUrl)

            var flyer: Flyer = Flyer(array[i],false,builder.toString())
            newFlyersArray.add(flyer)
        }

        mainViewInterface.populateMainView(newFlyersArray)
    }

    override fun sendError(message: String) {
        mainViewInterface.showToast(message)
    }
}