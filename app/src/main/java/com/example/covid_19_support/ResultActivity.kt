package com.example.covid_19_support

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class ResultActivity : AppCompatActivity() {

    var keywordSearchOption : Boolean = false
    var isValid : Boolean = true
    val db = FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)

        val resultID = ArrayList<Int>()
        val resultNAME = ArrayList<String>()

        val i = intent
        isValid = i.getBooleanExtra("isValid", true)

        /**
         * keyword 검색옵션을 선택했을 때
         */
        lateinit var keywordOptionList:ArrayList<String>
        if(i.hasExtra("keywordList")) {
            keywordSearchOption = true
            keywordOptionList = i.getStringArrayListExtra("keywordList") as ArrayList<String>
            for(i in keywordOptionList) {
                Log.i("keywordOption", i)
            }
        }

        val locationOptionList = i.getStringArrayListExtra("locationList") as ArrayList<String>
        for(i in locationOptionList) {
            Log.i("locationOption", i)
        }

        Log.i("isValid", isValid.toString())
        searchFB(keywordOptionList,locationOptionList)
        /**
         *파베에서 검색 후, 배열에 넣어준 후,
         */
        //아래는 임시
//        resultID.add(1)
//        resultID.add(2)
//        resultID.add(3)
//        resultNAME.add("국가지원1")
//        resultNAME.add("국가지원2")
//        resultNAME.add("국가지원3")
        var adapter:ResultAdapter = ResultAdapter(resultID,resultNAME)
        recyclerView.adapter = adapter
        //recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))\
    }
    fun searchFB(keyword:ArrayList<String> , location:ArrayList<String>):Int {
        // TODO: 2020-07-23 파베에서 읽는 코드를 넣어야함 인자랑 내용 수정 요망
        if(keywordSearchOption != false) {
            //db 연결후 검색 시작
            db.collection("corona").get().addOnSuccessListener {
                result ->
                for (document in result) {
                Log.i("db에서 읽기 --","${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener {
                exception ->
                Log.i("db에서 읽기(실패 --","Error:",exception)
            }
        } else {
            return -1 // false면 키워드 말고 option이랑 valid로 검색
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
