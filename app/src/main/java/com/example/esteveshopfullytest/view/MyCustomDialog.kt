package com.example.esteveshopfullytest.view

import android.content.DialogInterface
import android.os .Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.esteveshopfullytest.BaseApplication
import com.example.esteveshopfullytest.R
import com.example.esteveshopfullytest.analytics.FlyerOpenEventImpl
import com.example.esteveshopfullytest.analytics.FlyerSessionEventImpl
import com.example.esteveshopfullytest.model.Flyer
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso
import java.time.Duration
import java.util.*

class MyCustomDialog(val flyer: Flyer, val initSession: Calendar) : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var inflater = inflater.inflate(R.layout.details_dialog_fragment, container, false)

        val text = inflater.findViewById<TextView>(R.id.tv_title)
        text.text = flyer.title

        val img = inflater.findViewById<ImageView>(R.id.img_detail)
        Picasso.get().load(flyer.imgURL).networkPolicy(NetworkPolicy.OFFLINE).into(img)

        val btn = inflater.findViewById<Button>(R.id.btn_accept)
        btn.setOnClickListener{
            dialog?.dismiss()
        }

        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner);
        dialog!!.setCancelable(false)
        dialog!!.setCanceledOnTouchOutside(false)

        return inflater
    }

    override fun onStart() {
        super.onStart()
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.40).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // The ”flyer_session” event must be sent when the detail view is dismissed.
        // !flyer.read -> es el first_read, la primera vez se mandará un true, las demás un false
        val finishSession = Calendar.getInstance()
        val duration = (initSession.timeInMillis - finishSession.timeInMillis).toString()
        var flyerSession = FlyerSessionEventImpl(flyer.id,duration,!flyer.read)
        BaseApplication.instance.getStreamFully().process(flyerSession)

    }

}
