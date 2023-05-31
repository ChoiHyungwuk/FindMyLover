package com.hwchoi.FindMyLover.Activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hwchoi.FindMyLover.R
import com.hwchoi.FindMyLover.Utils.mBackPressedDelay

import net.daum.mf.map.api.MapView

private var backpressedTime: Long = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.main_toolbar))

        setContentView(R.layout.activity_main)

        val mapView = MapView(this)

        val mapViewContainer = findViewById<View>(R.id.map_view) as ViewGroup
        mapViewContainer.addView(mapView)

    }

    override fun onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + mBackPressedDelay) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, getString(R.string.back_pressed_text), Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backpressedTime + mBackPressedDelay) {
            finishAffinity();
        }
    }

}