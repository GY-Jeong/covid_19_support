package com.example.covid_19_support

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var initTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        //메인에 테스트 코드 작성
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initSpinner()
        startJob()
    }

    private fun initSpinner() {
        val location = locationArray()

        val searchOptionAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, location.locationList)

        locationOptionSpinner.adapter = searchOptionAdapter
        locationOptionSpinner.setSelection(0)

        val detailSearchOptionAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, arrayListOf<String>())

        locationOptionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                detailSearchOptionAdapter.clear()
                when(position) {
                    0 -> detailSearchOptionAdapter.addAll(location.option0)
                    1 -> detailSearchOptionAdapter.addAll(location.seoul)
                    2 -> detailSearchOptionAdapter.addAll(location.gyeonggi)
                    3 -> detailSearchOptionAdapter.addAll(location.busan)
                    4 -> detailSearchOptionAdapter.addAll(location.incheon)
                    5 -> detailSearchOptionAdapter.addAll(location.daegu)
                    6 -> detailSearchOptionAdapter.addAll(location.daejeon)
                    7 -> detailSearchOptionAdapter.addAll(location.gwangju)
                    8 -> detailSearchOptionAdapter.addAll(location.ulsan)
                    9 -> detailSearchOptionAdapter.addAll(location.gyeongnam)
                    10 -> detailSearchOptionAdapter.addAll(location.gyeongbuk)
                    11 -> detailSearchOptionAdapter.addAll(location.chungbuk)
                    12 -> detailSearchOptionAdapter.addAll(location.chungnam)
                    13 -> detailSearchOptionAdapter.addAll(location.jeonbuk)
                    14 -> detailSearchOptionAdapter.addAll(location.jeonnam)
                    15 -> detailSearchOptionAdapter.addAll(location.gwangwon)
                    16 -> detailSearchOptionAdapter.addAll(location.jeju)
                    17 -> detailSearchOptionAdapter.addAll(location.sejong)
                }
                detailOptionSpinner.adapter = detailSearchOptionAdapter
            }
        }
    }


    private fun startJob() {
        val i = Intent(this, ResultActivity::class.java)
    }

    /**
     * 뒤로가기 키 1.5초 안에 두번 누르면 프로그램 종료
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> {
                if ((System.currentTimeMillis() - initTime) > 1500) {
                    Toast.makeText(this, "종료하려면 한번 더 누르세요.", Toast.LENGTH_SHORT).show()
                    initTime = System.currentTimeMillis()
                } else {
                    finish()
                }
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}
