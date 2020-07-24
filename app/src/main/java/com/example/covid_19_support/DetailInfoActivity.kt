package com.example.covid_19_support

import android.content.Intent
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.detail_info.*


class DetailInfoActivity : AppCompatActivity() {
    var isFabOpen = false
    lateinit var fab_open : Animation
    lateinit var fab_close : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_info)
        init()
        fab_main.setOnClickListener {
            toggleFab()
        }
        fab_sub1.setOnClickListener {
            toggleFab()
            Toast.makeText(this, "Camera Open-!", Toast.LENGTH_SHORT).show()
        }
        fab_sub2.setOnClickListener {
            toggleFab()
            Toast.makeText(this, "Map Open-!", Toast.LENGTH_SHORT).show()
            val i = Intent(this, ChattingActivity::class.java)
            val id = "000000349400"
            i.putExtra("serviceID", id)
            startActivity(i)
        }
    }

    private fun init() {
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
    }

    private fun toggleFab() {
        if (isFabOpen) {
            fab_sub1.startAnimation(fab_close)
            fab_sub2.startAnimation(fab_close)
            fab_sub1_text.startAnimation(fab_close)
            fab_sub2_text.startAnimation(fab_close)
            fab_sub1.isClickable = false
            fab_sub2.isClickable = false
            isFabOpen = false
        } else {
            fab_sub1.startAnimation(fab_open)
            fab_sub2.startAnimation(fab_open)
            fab_sub1_text.startAnimation(fab_open)
            fab_sub2_text.startAnimation(fab_open)
            fab_sub1.isClickable = true
            fab_sub2.isClickable = true
            isFabOpen = true
        }
    }
}
