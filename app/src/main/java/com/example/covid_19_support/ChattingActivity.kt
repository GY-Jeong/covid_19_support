package com.example.covid_19_support

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_chatting.*

class ChattingActivity : AppCompatActivity() {
    val messageList = ArrayList<Message>()
    lateinit var db: FirebaseFirestore
    lateinit var id: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        db = FirebaseFirestore.getInstance()
        val i = intent
        id = i.getStringExtra("serviceID")!!
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
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
        }
    }

    private fun connectFB() {
        val doc = db.collection("chatting").document(id)
        val col = doc.collection("message")
        col.get()
            .addOnSuccessListener { result ->
                for (message in result) {
                    Log.i("hello", "${message["제목"]} , ${message["내용"]}")
                    var newMessage = Message(
                        message["제목"] as String,
                        message["내용"] as String, message["시간"] as String
                    )
                    messageList.add(newMessage)
                }
            }
            .addOnFailureListener { exception ->
                Log.e("hello", "Error getting documents.", exception)
            }

        messageView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val adapter = MessageAdapter(messageList)
        messageView.adapter = adapter
    }
}
