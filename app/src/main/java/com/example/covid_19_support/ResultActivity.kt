package com.example.covid_19_support

import android.os.Build.ID
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.common.primitives.UnsignedBytes.toInt
import com.google.firebase.firestore.FirebaseFirestore


class ResultActivity : AppCompatActivity() {

    var keywordSearchOption : Boolean = false
    var isValid : Boolean = true
    val db = FirebaseFirestore.getInstance()

    var resultID = ArrayList<String>()
    var resultNAME = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_result)

        val recyclerView = findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)



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
        resultID.clear()
        resultNAME.clear()

        if(searchFB(keywordOptionList,locationOptionList) == 0 ) {
            Log.i("searchFB의 정상적인 실행 완료 ㅇㅇ","")
        }
        /**
         *파베에서 검색 후, 배열에 넣어준 후,
         */
        for(i in resultID) {
            Log.i("어댑터 달기 전 resultID 춝력",i)
        }
        for(i in resultNAME) {
            Log.i("어댑터 달기 전 resultNAME 춝력",i)
        }
        var adapter:ResultAdapter = ResultAdapter(resultID,resultNAME)
        recyclerView.adapter = adapter
        //adapter.notifyDataSetChanged()
        //recyclerView.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))\
    }
    fun searchFB(keyword:ArrayList<String> , location:ArrayList<String>):Int {
        // TODO: 2020-07-23 파베에서 읽는 코드를 넣어야함 인자랑 내용 수정 요망

        if(keywordSearchOption != false) {
            //db 연결후 검색 시작
            db.collection("corona").get().addOnSuccessListener {
                result ->
                for (document in result) {
                    if (location[0].equals("전체")) {
                        for(word in keyword) {
                            if((document["지원내용"] as String).toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                } else {
                                    resultID.add(document["ID"] as String)
                                    resultNAME.add(document["서비스명"] as String)
                                }
                            }
                            if((document["지원대상"] as String).toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                } else {
                                    resultID.add(document["ID"] as String)
                                    resultNAME.add(document["서비스명"] as String)
                                }
                            }
                            if((document["서비스명"] as String).toString().contains(word)) {
                                if(resultID.contains(document["ID"] as String)) {
                                    break
                                } else {
                                    resultID.add(document["ID"] as String)
                                    resultNAME.add(document["서비스명"] as String)
                                }
                            }
                        }
                    }
                    else if(location[0].equals((document["지역 단위"] as String).toString())) {
                        Log.i("location1",location[0] + (document["지역 단위"] as String).toString())
                        if(location[1].equals((document["소관기관 명"] as String).toString())) {
                            Log.i("location2",location[1] + (document["소관기관 명"] as String).toString())
                            Log.i("db에서 읽기 --","${document.id} => ${document["ID"]} &&&& ${document["지원내용"]} &&& ${document["지원대상"]} &&& ${document["서비스명"]}")
                            for(word in keyword) {
                                Log.i("for문 입성","입성했다")
                                if((document["지원내용"]).toString().contains(word)) {
                                    Log.i("내용과 word가 일치","일치")
                                    if(resultID.contains(document["ID"] as String)) {
                                        break
                                    } else {
                                        resultID.add(document["ID"] as String)
                                        resultNAME.add(document["서비스명"] as String)
                                        for(i in resultID) {
                                            Log.i("resultID 춝력",i)
                                        }
                                        for(i in resultNAME) {
                                            Log.i("resultNAME 춝력",i)
                                        }
                                    }
                                }
                                if((document["지원대상"] as String).toString().contains(word)) {
                                    Log.i("대상과 word가 일치","일치")
                                    if(resultID.contains(document["ID"] as String)) {
                                        break
                                    } else {
                                        resultID.add(document["ID"] as String)
                                        resultNAME.add(document["서비스명"] as String)
                                        for(i in resultID) {
                                            Log.i("resultID 춝력",i)
                                        }
                                        for(i in resultNAME) {
                                            Log.i("resultNAME 춝력",i)
                                        }
                                    }
                                }
                                if((document["서비스명"] as String).toString().contains(word)) {
                                    Log.i("서비스명과 word가 일치","일치")
                                    if(resultID.contains(document["ID"] as String)) {
                                        break
                                    } else {
                                        resultID.add(document["ID"] as String)
                                        resultNAME.add(document["서비스명"] as String)
                                        for(i in resultID) {
                                            Log.i("resultID 춝력",i)
                                        }
                                        for(i in resultNAME) {
                                            Log.i("resultNAME 춝력",i)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    else if(location[0].equals("기타")) {
                        if(location[1].equals((document["소관기관 명"] as String).toString())) {
                            for(word in keyword) {
                                if((document["지원내용"] as String).toString().contains(word)) {
                                    if(resultID.contains(document["ID"] as String)) {
                                        break
                                    } else {
                                        resultID.add(document["ID"] as String)
                                        resultNAME.add(document["서비스명"] as String)
                                    }
                                }
                                if((document["지원대상"] as String).toString().contains(word)) {
                                    if(resultID.contains(document["ID"] as String)) {
                                        break
                                    } else {
                                        resultID.add(document["ID"] as String)
                                        resultNAME.add(document["서비스명"] as String)
                                    }
                                }
                                if((document["서비스명"] as String).toString().contains(word)) {
                                    if(resultID.contains(document["ID"] as String)) {
                                        break
                                    } else {
                                        resultID.add(document["ID"] as String)
                                        resultNAME.add(document["서비스명"] as String)
                                    }
                                }
                            }
                        }
                    }
                    Log.i("방대한 db 하나 끝","done")
                }
                Log.i("성공시 하는 코드 모두 끝","done")
            }
            Log.i("ALL DONE모두 끝","done")
//            .addOnFailureListener {
//                exception ->
//                Log.i("db에서 읽기(실패 --","Error:",exception)
//            }
        } else {// false면 키워드 말고 option이랑 valid로 검색
            db.collection("corona").get().addOnSuccessListener {
                    result ->
                for (document in result) {
                    Log.i("db에서 읽기 --","${document.id} => ${document["ID"]} &&&& ${document["지원내용"]} &&& ${document["지원대상"]} &&& ${document["서비스명"]}")
                    if (location[0].equals("전체")) {
                        resultID.add(document["ID"] as String)
                        resultNAME.add(document["서비스명"] as String)
                        break
                    }
                    else if(location[0].equals((document["지역 단위"] as String).toString())) {
                        if(location[1].equals((document["소관기관 명"] as String).toString())) {
                            resultID.add(document["ID"] as String)
                            resultNAME.add(document["서비스명"] as String)
                            break
                        }
                    }
                    else if(location[0].equals("기타")) {
                        if(location[1].equals((document["소관기관 명"] as String).toString())) {
                            resultID.add(document["ID"] as String)
                            resultNAME.add(document["서비스명"] as String)
                            break
                        }
                    }
                }
            }
            return -1
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
