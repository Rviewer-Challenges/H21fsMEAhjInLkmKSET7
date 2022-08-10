package com.ucielcorp.programmerchat.views

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.ucielcorp.programmerchat.R
import com.ucielcorp.programmerchat.modals.User
import com.ucielcorp.programmerchat.presenters.UsersAdapter
import kotlinx.android.synthetic.main.activity_user_list.*

class UserListActivity : AppCompatActivity() {

    // Definición de variable principales
    private lateinit var recyclerView: RecyclerView
    private lateinit var userArrayList : ArrayList<User>
    private lateinit var userAdapter : UsersAdapter
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        returnButton.setOnClickListener {
            startActivity(Intent(this, ListOfChatsActivity::class.java))
        }

        recyclerView = findViewById(R.id.listUserRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        userArrayList = arrayListOf()

        userAdapter = UsersAdapter(this, userArrayList)
        recyclerView.setAdapter(userAdapter)

        initView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initView() {

        db = FirebaseFirestore.getInstance()
        db.collection("user").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(
                value: QuerySnapshot?,
                error: FirebaseFirestoreException?
            ) {
                if (error != null){
                    Log.e("Firestore Error: ", error.message.toString())
                    return
                }

                for(dc : DocumentChange in value?.documentChanges!!){
                    if(dc.type == DocumentChange.Type.ADDED){
                        userArrayList.add(dc.document.toObject(User::class.java))
                    }
                }

                userAdapter.notifyDataSetChanged()
            }
        })
    }
}