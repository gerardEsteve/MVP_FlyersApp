package com.example.esteveshopfullytest.view

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import com.example.esteveshopfullytest.BaseApplication
import com.example.esteveshopfullytest.R
import com.example.esteveshopfullytest.analytics.FlyerOpenEventImpl
import com.example.esteveshopfullytest.model.Flyer
import com.example.esteveshopfullytest.databinding.ActivityMainBinding
import com.example.esteveshopfullytest.presenters.FlyersListPresenter
import com.example.esteveshopfullytest.view.adapters.MainAdapter
import java.util.*
import kotlin.collections.ArrayList

class MainViewActivity : AppCompatActivity(), MainViewInterface {

    private lateinit var binding: ActivityMainBinding

    private var flyersListPresenter = FlyersListPresenter(this@MainViewActivity)
    private var mainAdapter = MainAdapter(this@MainViewActivity)
    private lateinit var progressDialog:ProgressDialog
    private lateinit var toggleSwitch: SwitchCompat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.include.gridview.adapter = mainAdapter

        binding.include.gridview.setOnItemClickListener(::itemClicked)

        progressDialog = ProgressDialog(this@MainViewActivity)

        flyersListPresenter.initializeMainView()

    }

    fun itemClicked( adapter: AdapterView<*>, view: View, position: Int, l: Long) {
            flyersListPresenter.onItemClickedAtPosition(position)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        toggleSwitch = menu.findItem(R.id.switch_action_bar).actionView.findViewById(R.id.switch_button)

        toggleSwitch.setOnCheckedChangeListener{ _, isChecked ->
                flyersListPresenter.toggleClicked(isChecked)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun populateMainView(data: ArrayList<Flyer>) {
        mainAdapter.setFlyers(data)
    }

    override fun showToast(message: String) {
        Toast.makeText(
            applicationContext, message,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun showLoading(show: Boolean) {
        if(show){
            if(!progressDialog.isShowing){
                progressDialog.setTitle("Please Wait")
                progressDialog.setMessage("Loading some awesome flyers...")
                progressDialog.show()
            }
        }
        else {
            if(progressDialog.isShowing){
                progressDialog.dismiss()
            }
        }
    }

    override fun showFlyerDetails(position: Int) {
        val flyerClicked = mainAdapter.getItem(position)
        MyCustomDialog(flyerClicked,Calendar.getInstance()).show(supportFragmentManager, "MyCustomDialog")
    }

    override fun sendAnalyticsFlyerOpen(position: Int) {
        val flyerClicked = mainAdapter.getItem(position)
        // !flyerClicked.read -> es el first_read, la primera vez se mandará un true, las demás un false
        val flyerOpen = FlyerOpenEventImpl(flyerClicked.retailer_id,flyerClicked.id,flyerClicked.title,position,!flyerClicked.read)
        BaseApplication.instance.getStreamFully().process(flyerOpen)
    }

    override fun setFlyerRead(position: Int) {
        mainAdapter.getItem(position).read = true
        mainAdapter.notifyDataSetChanged()
    }

    override fun showReadFlyers(showRead: Boolean) {
        mainAdapter.toggleClicked(showRead)
    }
}