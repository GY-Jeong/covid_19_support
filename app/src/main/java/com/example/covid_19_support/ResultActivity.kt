package com.example.covid_19_support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ResultActivity : AppCompatActivity() {

    var keywordSearchOption : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val i = intent
        val isValid = i.getBooleanExtra("isValid", true)

        /**
         * keyword 검색옵션을 선택했을 때
         */

        if(i.hasExtra("keywordList")) {
            keywordSearchOption = true
            val keywordOptionList = i.getStringArrayListExtra("keywordList") as ArrayList<String>
            for(i in keywordOptionList) {
                Log.i("keywordOption", i)
            }
        }

        val locationOptionList = i.getStringArrayListExtra("locationList") as ArrayList<String>
        for(i in locationOptionList) {
            Log.i("locationOption", i)
        }

        Log.i("isValid", isValid.toString())
    }
    fun searchFB(keyword:ArrayList<String> , location:ArrayList<String>):Int {
        // TODO: 2020-07-23 파베에서 읽는 코드를 넣어야함 인자랑 내용 수정 요망
        if(keywordSearchOption != false) {
            //db 연결후 검색 시작
        } else {
            return -1 // false면 검색 x
        }
        return 0
    }

    //지역으로 검색
    //만약에 locationOptionList[0] != 전체

    //keywordSearchOption == true
    //keywordSearchOption == flse -> 검색안해도됨

    //기간 case 4갠데
    //신경써야됨
}
