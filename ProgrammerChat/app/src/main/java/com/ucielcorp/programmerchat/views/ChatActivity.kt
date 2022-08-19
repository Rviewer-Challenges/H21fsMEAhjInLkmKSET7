package com.ucielcorp.programmerchat.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ucielcorp.programmerchat.R
import kotlinx.android.synthetic.main.activity_chat.*

class ChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        returnRoomButton.setOnClickListener {
            startActivity(Intent(this, ListOfChatsActivity::class.java))
        }
    }
}