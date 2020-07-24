package com.example.covid_19_support

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.search_result.*
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {
    var keywordSearchOption: Boolean = false
    var isValid: Boolean = true
    val db = FirebaseFirestore.getInstance()
    var entryNum = 0
    var dateInt = 0

    var resultID = ArrayList<String>()
    var resultNAME = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)
        getDate()

        val i = intent
        isValid = i.getBooleanExtra("isValid", true)

        /**
         * keyword 검색옵션을 선택했을 때
         */
        lateinit var keywordOptionList: ArrayList<String>

        if (i.hasExtra("keywordList")) {
            keywordSearchOption = true
            keywordOptionList = i.getStringArrayListExtra("keywordList") as ArrayList<String>
            for (i in keywordOptionList) {
                Log.i("keywordOption", i)
            }
        } else {
            keywordOptionList = ArrayList()
        }

        val locationOptionList = i.getStringArrayListExtra("locationList") as ArrayList<String>
        for (i in locationOptionList) {
            Log.i("locationOption", i)
        }

        Log.i("isValid", isValid.toString())

        searchFB(keywordOptionList, locationOptionList)
        /**
         *파베에서 검색 후, 배열에 넣어준 후,
         */
    }

    private fun getDate() {
        val cal = Calendar.getInstance()
        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH) + 1
        val date = cal.get(Calendar.DATE)
        dateInt = (year.toString() + String.format("%02d", month) + String.format("%02d", date)).toInt()
    }

    fun searchFB(keyword: ArrayList<String>, location: ArrayList<String>){
        // TODO: 2020-07-23 파베에서 읽는 코드를 넣어야함 인자랑 내용 수정 요망
        if (keywordSearchOption) {
            //db 연결후 검색 시작
            db.collection("corona").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (location[0] == "전체" || (location[0] == document["지역 단위"] as String && (location[1] == "전체" || location[1] == document["소관기관 명"] as String))) {
                        for (word in keyword) {
                            if (document["지원내용"].toString().contains(word) || document["지원대상"].toString().contains(word) || document["서비스명"].toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                }
                                var add = false
                                if(isValid) {
                                    val v = document["신청기한"].toString().replace(" ", "")
                                    if(v.length == 1) {
                                        add = true
                                    }
                                    else if(v.length == 9) {
                                        if(v.startsWith(",") && v.substring(1).toInt() <= dateInt) {
                                            add = true
                                        } else if(v.substring(0,8).toInt() < dateInt) {
                                            add = true
                                        }
                                    }
                                    else {
                                        if(v.substring(0,8).toInt() <= dateInt && v.substring(9).toInt() >= dateInt) {
                                            add = true
                                        }
                                    }
                                } else {
                                    add = true
                                }
                                if(add) {
                                    resultID.add(document["ID"].toString())
                                    resultNAME.add(document["서비스명"].toString())
                                    Log.i("check", "${document["ID"]}, ${document["서비스명"]}, ${resultID[0]}")
                                }
                            }
                        }
                    }
                }
                attachAdapter()
            }
        } else {// false면 키워드 말고 option이랑 valid로 검색
            db.collection("corona").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (location[0] == "전체" || (location[0] == document["지역 단위"] as String && (location[1] == "전체" || location[1] == document["소관기관 명"] as String))) {
                        var add = false
                        if(isValid) {
                            val v = document["신청기한"].toString().replace(" ", "")
                            if(v.length == 1) {
                                add = true
                            }
                            else if(v.length == 9) {
                                if(v.startsWith(",") && v.substring(1).toInt() <= dateInt) {
                                    add = true
                                } else if(v.substring(0,8).toInt() < dateInt) {
                                    add = true
                                }
                            }
                            else {
                                if(v.substring(0,8).toInt() <= dateInt && v.substring(9).toInt() >= dateInt) {
                                    add = true
                                }
                            }
                        } else {
                            add = true
                        }
                        if(add) {
                            resultID.add(document["ID"].toString())
                            resultNAME.add(document["서비스명"].toString())
                            Log.i("check", "${document["ID"]}, ${document["서비스명"]}, ${resultID[0]}")
                        }
                    }
                }
                attachAdapter()
            }
        }
    }

    private fun attachAdapter() {
        entryNum = resultID.size
        resultEntryView.text = "${entryNum}개의 서비스가 검색되었습니다."
        Log.i("count_ID_2", resultID.size.toString())
        Log.i("count_NAME_2", resultNAME.size.toString())
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val adapter = ResultAdapter(resultID, resultNAME)

        adapter.itemClickListenner = object : ResultAdapter.OnItemClickListenner {
            override fun OnItemClick(holder: ResultAdapter.ViewHolder, view: View, data: String, position: Int) {
                val i = Intent(applicationContext, DetailInfoActivity::class.java)
                i.putExtra("ServiceID", resultID[position])
                startActivity(i)
            }
        }
        recyclerView.adapter = adapter
    }
}
