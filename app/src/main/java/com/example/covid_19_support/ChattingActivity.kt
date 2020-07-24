package com.example.covid_19_support

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class ChattingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatting)
        val i = intent
        val id = i.getStringExtra("serviceID")
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show()
    }
}
