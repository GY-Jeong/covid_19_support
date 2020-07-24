package com.example.covid_19_support

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.android.synthetic.main.activity_chatting.*

class ChattingActivity : AppCompatActivity() {
    val messageList = ArrayList<Message>()
    lateinit var db: FirebaseFirestore
    lateinit var id: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        val i = intent
        id = i.getStringExtra("serviceID")!!
        db = FirebaseFirestore.getInstance()
        init()
        connectFB()
    }

    private fun init() {
        insertBtn.setOnClickListener {
            val current = System.currentTimeMillis()
            Log.i("time", ":$current")
            val msg = hashMapOf(
                "제목" to editText.text.toString(),
                "시간" to current.toString(),
                "내용" to editText2.text.toString()
            )
            editText.text.clear()
            editText2.text.clear()

            val doc = db.collection("chatting").document(id).collection("message").document().set(msg).addOnSuccessListener {
                Log.i("set","파베에 쓰는 것 성공!")
            }.addOnFailureListener {
                Log.i("set","파베에 쓰는 것 실패ㅠㅠ")
            }
            connectFB()
        }
    }

    private fun connectFB() {
        messageList.clear()
        val doc = db.collection("chatting").document(id)
        val col = doc.collection("message").orderBy("시간",Query.Direction.ASCENDING)
        col.get()
            .addOnSuccessListener { result ->
                for (message in result) {
                    Log.i("hello", "${message["제목"]} , ${message["내용"]}")
                    val newMessage = Message(
                        message["제목"] as String,
                        message["내용"] as String, message["시간"] as String
                    )
                    messageList.add(newMessage)
                }
                messageView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
                val adapter = MessageAdapter(messageList)
                messageView.adapter = adapter
            }
            .addOnFailureListener { exception ->
                Log.e("hello", "Error getting documents.", exception)
            }
    }
}
