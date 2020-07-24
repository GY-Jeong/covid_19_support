package com.example.covid_19_support

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.detail_info.*


class DetailInfoActivity : AppCompatActivity() {
    var isFabOpen = false
    lateinit var fab_open : Animation
    lateinit var fab_close : Animation
    lateinit var serviceID : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_info)
        init()
        fab_main.setOnClickListener {
            toggleFab()
        }
        fab_sub1.setOnClickListener {
            toggleFab()
        }
        fab_sub2.setOnClickListener {
            toggleFab()
            val i = Intent(this, ChattingActivity::class.java)
            val id = serviceID
            i.putExtra("serviceID", id)
            startActivity(i)
        }
    }

    private fun init() {
        serviceID = intent.getStringExtra("ServiceID")!!
        Log.i("ServiceID", serviceID)
        getFB()
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
    }

    private fun getFB() {
        var infoDesk:String
        var supTarget:String
        var supContents:String
        var applyPeriod:String
        var requireDoc:String
        var infoNum:String
        var serviceAddr:String

        val db = FirebaseFirestore.getInstance()
        db.collection("corona").whereEqualTo("ID",serviceID).get()
            .addOnSuccessListener {
                documents ->
                for( item in documents) {
                    infoDesk = item["문의처 명"].toString()
                    supTarget = item["지원대상"].toString()
                    supContents = item["지원내용"].toString()
                    applyPeriod = item["신청기한"].toString()
                    requireDoc = item["구비서류"].toString()
                    infoNum = item["문의처 전화번호"].toString()
                    serviceAddr = item["서비스 상세 주소"].toString()

                    Log.i("getFB", infoDesk.toString())
                    Log.i("getFB", supTarget.toString())
                    Log.i("getFB", supContents.toString())
                    Log.i("getFB", applyPeriod.toString())
                    Log.i("getFB", requireDoc.toString())
                    Log.i("getFB", infoNum.toString())
                    Log.i("getFB", serviceAddr.toString())
                }

            }
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
