package com.application.internet.connections.and.capabilities.using.real_time

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.view.WindowManager.LayoutParams
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private lateinit var TextView1: TextView
    private lateinit var TextView2: TextView
    private lateinit var cld: LiveDataInternetConnections
    var networkDialog:Dialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        TextView1 = findViewById(R.id.connected)
        TextView2 = findViewById(R.id.not_connected)
        cld = LiveDataInternetConnections(application)
        cld.observe(this) { isConnected ->
            if (isConnected) {
                TextView1.visibility = View.VISIBLE
                TextView2.visibility = View.GONE
                networkDialog?.dismiss()
            } else {
                println("Network--->${"Hide"}")
                networkDialog()
                TextView1.visibility = View.GONE
                TextView2.visibility = View.VISIBLE
                networkDialog?.show()
            }
        }
    }

    private fun networkDialog() {
        networkDialog = Dialog(this, android.R.style.Theme_Translucent_NoTitleBar)
        networkDialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        networkDialog?.setContentView(R.layout.internet_dialog)
        val window: Window? = networkDialog?.window
        val wlp: LayoutParams? = window?.attributes
        wlp?.gravity = Gravity.CENTER
        wlp?.flags = wlp?.flags?.and(LayoutParams.FLAG_BLUR_BEHIND.inv())
        window?.attributes = wlp
        networkDialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)

//        dialog.show()
        val restart = networkDialog?.findViewById<View>(R.id.view49)
        val network = networkDialog?.findViewById<View>(R.id.view48)
        network?.setOnClickListener { v: View? ->
            val intent2 = Intent(Settings.ACTION_WIRELESS_SETTINGS)
            startActivity(intent2)
        }
        restart?.setOnClickListener { v: View? ->
            recreate()
            Log.d("clickedButton", "yes")
        }

    }
}