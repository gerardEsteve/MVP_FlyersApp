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

    private var flyerRepository = FlyerRepository(this@FlyersListPresenter) // TODO falta el DAO

    fun initializeMainView(){
        showLoading(true)
        CoroutineScope(Dispatchers.IO).launch {
            flyerRepository.obtainFlyersData()
        }

    }

    fun onItemClickedAtPosition(position: Int) {
        mainViewInterface.sendAnalyticsFlyerOpen(position)
        mainViewInterface.setFlyerRead(position)
        mainViewInterface.showFlyerDetails(position)
     //   mainViewInterface.showToast("You CLicked $position")
    }

    fun toggleClicked(isChecked: Boolean){
        mainViewInterface.showReadFlyers(isChecked)
    }

    fun showLoading(show:Boolean) {
        mainViewInterface.showLoading(show)
    }


    override fun sendFlyers(array : ArrayList<Flyer>) {
        showLoading(false)
        mainViewInterface.populateMainView(array)
    }

}