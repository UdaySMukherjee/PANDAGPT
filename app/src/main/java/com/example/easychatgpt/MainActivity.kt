package com.example.easychatgpt

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.bumptech.glide.Glide
import com.example.easychatgpt.utils.NetworkConnectivityObserver
import com.example.easychatgpt.utils.NetworkStatus
import com.google.android.material.snackbar.Snackbar
import android.widget.ImageView

class MainActivity : AppCompatActivity(){

    private val networkConnectivityObserver: NetworkConnectivityObserver by lazy {
        NetworkConnectivityObserver(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // here setKeepOnScreenCondition false so, activity redirect another activity
        // and some api call here
        // if setKeepOnScreenCondition true so, activity code not redirect another activity
        splashScreen.setKeepOnScreenCondition { false }

        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            "No Internet Connection",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Wifi") {
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

        networkConnectivityObserver.observe(this) {
            when (it) {
                NetworkStatus.Available -> {
                    if (snackbar.isShown) {
                        snackbar.dismiss()
                    }
                }
                else -> {
                    snackbar.show()
                }
            }
        }


    }
}