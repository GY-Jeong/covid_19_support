package com.example.covid_19_support

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.search_result.*

class ResultActivity : AppCompatActivity() {
    var keywordSearchOption: Boolean = false
    var isValid: Boolean = true
    val db = FirebaseFirestore.getInstance()


    var resultID = ArrayList<String>()
    var resultNAME = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)


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

    fun searchFB(keyword: ArrayList<String>, location: ArrayList<String>){
        // TODO: 2020-07-23 파베에서 읽는 코드를 넣어야함 인자랑 내용 수정 요망
        if (keywordSearchOption) {
            //db 연결후 검색 시작
            db.collection("corona").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (location[0] == "전체") {
                        for (word in keyword) {
                            if (document["지원내용"].toString().contains(word) || document["지원대상"].toString().contains(word) || (document["서비스명"] as String).toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                }
                                resultID.add(document["ID"] as String)
                                resultNAME.add(document["서비스명"] as String)
                                Log.i("check", "${document["ID"].toString()}, ${document["서비스명"].toString()}")
                            }
                        }
                    } else if (location[0] == document["지역 단위"].toString() && location[1] == "전체") {
                        for (word in keyword) {
                            if (document["지원내용"].toString().contains(word) || document["지원대상"].toString().contains(word) || (document["서비스명"] as String).toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                }
                                resultID.add(document["ID"] as String)
                                resultNAME.add(document["서비스명"] as String)
                                Log.i("check", "${document["ID"].toString()}, ${document["서비스명"].toString()}")
                            }
                        }
                    } else if (location[0] == document["지역 단위"].toString() && location[1] == document["소관기관 명"].toString()) {
                        for (word in keyword) {
                            if (document["지원내용"].toString().contains(word) || document["지원대상"].toString().contains(word) || (document["서비스명"] as String).toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                }
                                resultID.add(document["ID"] as String)
                                resultNAME.add(document["서비스명"] as String)
                                Log.i("check", "${document["ID"].toString()}, ${document["서비스명"].toString()}")
                            }
                        }
                    }
                }
                attachAdapter()
            }
        } else {// false면 키워드 말고 option이랑 valid로 검색
            db.collection("corona").get().addOnSuccessListener { result ->
                for (document in result) {
                    if (location[0] == "전체") {
                        resultID.add(document["ID"].toString())
                        resultNAME.add(document["서비스명"].toString())
                        Log.i("check", "${document["ID"]}, ${document["서비스명"]}, ${resultID[0]}")
                    } else if (location[0] == document["지역 단위"] as String && location[1] == "전체") {
                        resultID.add(document["ID"].toString())
                        resultNAME.add(document["서비스명"].toString())
                        Log.i("check", "${document["ID"]}, ${document["서비스명"]}, ${resultID[0]}")
                    } else if (location[0] == document["지역 단위"] as String && location[1] == document["소관기관 명"] as String) {
                        resultID.add(document["ID"].toString())
                        resultNAME.add(document["서비스명"].toString())
                        Log.i("check", "${document["ID"]}, ${document["서비스명"]}, ${resultID[0]}")
                    }
                }
                attachAdapter()
            }
        }
    }

    private fun attachAdapter() {
        Log.i("count_ID_2", resultID.size.toString())
        Log.i("count_NAME_2", resultNAME.size.toString())
        for (i in resultID) {
            Log.i("어댑터 달기 전 resultID 춝력", i)
        }
        for (i in resultNAME) {
            Log.i("어댑터 달기 전 resultNAME 춝력", i)
        }

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
