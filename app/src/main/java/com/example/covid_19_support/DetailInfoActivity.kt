package com.example.covid_19_support

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
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
            dialogShow()
        }
        fab_sub2.setOnClickListener {
            toggleFab()
            val i = Intent(this, ChattingActivity::class.java)
            val id = serviceID
            i.putExtra("serviceID", id)
            startActivity(i)
        }
    }

    private fun dialogShow() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("내용에 맞지 않는 정보가 포함되어있나요?.").setTitle("오류 신고")
        builder.setPositiveButton("예") {
            _, _ ->
            //오류 횟수 1 증가
        }
        builder.setNegativeButton("아니요") {
                _, _ ->
        }
        val dlg = builder.create()
        dlg.show()
    }

    private fun init() {
        //serviceID = intent.getStringExtra("ServiceID")!!
        //Log.i("ServiceID", serviceID)
        serviceID = "000000349400"
        getFB()
        fab_open = AnimationUtils.loadAnimation(this, R.anim.fab_open)
        fab_close = AnimationUtils.loadAnimation(this, R.anim.fab_close)
    }

    private fun getFB() {
        lateinit var infoDesk:String
        lateinit var supTarget:String
        lateinit var supContents:String
        lateinit var applyPeriod:String
        lateinit var requireDoc:String
        lateinit var infoNum:String
        lateinit var serviceAddr:String
        lateinit var serviceName:String

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
                    serviceName = item["서비스명"].toString()

                    Log.i("getFB", infoDesk)
                    Log.i("getFB", supTarget)
                    Log.i("getFB", supContents)
                    Log.i("getFB", applyPeriod)
                    Log.i("getFB", requireDoc)
                    Log.i("getFB", infoNum)
                    Log.i("getFB", serviceAddr)
                }
                serviceNameView.text = serviceName
                infoDeskView.text = infoDesk
                supTargetView.text = supTarget
                supContentsView.text = supContents
                applyPeriodView.text = applyPeriod
                requireDocView.text = requireDoc
                infoNumView.text = infoNum
                serviceAddrView.text = serviceAddr
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
