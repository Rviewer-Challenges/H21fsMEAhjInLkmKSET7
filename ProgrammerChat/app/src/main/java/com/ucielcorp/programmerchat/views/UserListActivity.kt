package com.ucielcorp.programmerchat.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ucielcorp.programmerchat.R
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        returnButton.setOnClickListener {
            startActivity(Intent(this, ListOfChatsActivity::class.java))
        }
    }
}