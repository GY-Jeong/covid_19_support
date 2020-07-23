package com.example.covid_19_support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class ResultActivity : AppCompatActivity() {

    var keywordSearchOption : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)

        val i = intent
        val isValid = i.getBooleanExtra("isValid", true)

        /**
         * keyword 검색옵셔을 선택했을 때
         */

        if(i.hasExtra("keywordList")) {
            keywordSearchOption = true
            val keywordOptionList = i.getStringArrayListExtra("keywordList")
            for(i in keywordOptionList) {
                Log.i("keywordOption", i)
            }
        }

        val locationOptionList = i.getStringArrayListExtra("locationList")
        for(i in locationOptionList) {
            Log.i("locationOption", i)
        }
        Log.i("isvalid", isValid.toString())
    }
}
