package com.example.covid_19_support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
        id = i.getStringExtra("serviceID")
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
        connectFB()
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

        var adapter = MessageAdapter(messageList)
        messageView.adapter = adapter
    }

}
