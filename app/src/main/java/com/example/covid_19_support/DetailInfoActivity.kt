package com.example.covid_19_support

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.detail_info.*


class DetailInfoActivity : AppCompatActivity() {
    var isFabOpen = false
    lateinit var fab_open : Animation
    lateinit var fab_close : Animation
    lateinit var serviceID : String
    lateinit var db : FirebaseFirestore
    var errorCount : Long = 0
    lateinit var query : Task<QuerySnapshot>

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
        builder.setMessage("내용에 맞지 않는 정보가 포함되어있나요?\n현재 오류 신고 횟수는 ${errorCount}회 입니다.").setTitle("오류 신고")
        builder.setPositiveButton("아니요") {
                _, _ ->
        }
        builder.setNegativeButton("예") {
            _, _ ->
            //오류 횟수 1 증가
            errorCount++
            val docRef = db.collection("corona").document(serviceID)
            docRef.update("오류 신고 수", errorCount)
            Toast.makeText(this, "오류 신고가 접수되었습니다", Toast.LENGTH_SHORT).show()
            attachErrorCountView()
        }
        val dlg = builder.create()
        dlg.show()
    }

    private fun init() {
        serviceID = intent.getStringExtra("ServiceID")!!
        Log.i("ServiceID", serviceID)
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

        db = FirebaseFirestore.getInstance()
        query = db.collection("corona").whereEqualTo("ID",serviceID).get()
        query
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
                    errorCount = item["오류 신고 수"] as Long

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

                attachErrorCountView()
            }
    }

    private fun attachErrorCountView() {
        if(errorCount >= 5) {
            errorCountView.text = "이 서비스의 정보 오류 접수는 ${errorCount}건입니다.\n정보 확인에 유의하세요!"
        } else {
            errorCountView.text = "이 서비스의 정보 오류 접수는 ${errorCount}건입니다."
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
