package com.ucielcorp.chatfirenase.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.ucielcorp.chatfirenase.Models.Message
import com.ucielcorp.chatfirenase.Presenters.ChatAdapter
import com.ucielcorp.chatfirenase.R
import com.vanniktech.emoji.EmojiPopup
import com.vanniktech.emoji.EmojiTextView
import kotlinx.android.synthetic.main.activity_room_chat_acticity.*
import java.util.*
import kotlin.collections.ArrayList

class RoomChatActicity : AppCompatActivity() {
    // Declaraciones de variables para el RecyclerView
    private lateinit var recyclerView: RecyclerView
    private lateinit var chatArrayList : ArrayList<Message>
    private lateinit var chatAdapter : ChatAdapter

    // Declaración de variables de Firebase
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_chat_acticity)

        //Inicializamos la variable firebaseAuth
        auth = FirebaseAuth.getInstance()

        // Toolbar Config
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // RecyclerView Conf
        recyclerView = findViewById(R.id.usersChatRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)

        chatArrayList = arrayListOf()

        chatAdapter = ChatAdapter(this, chatArrayList)
        recyclerView.setAdapter(chatAdapter)

        emojiConfig()
        initViews()
    }

    private fun emojiConfig(){
        // Inicializamos el emoji popup
        val popup = EmojiPopup.Builder.fromRootView(chatLayout).build(messageText)

        btEmoji.setOnClickListener { popup.toggle() }
    }

    // Función para mostrar los chats por pantalla
    private fun initViews(){

        sendMessageButton.setOnClickListener {view ->
            val emojiTextView : EmojiTextView = LayoutInflater
                .from(view.context)
                .inflate(R.layout.emoji_text_view, linearLayout2, false) as EmojiTextView

            emojiTextView.setText(messageText.text.toString())
            //linearLayout2.addView(emojiTextView)

            sendMessage()
        }

        db = FirebaseFirestore.getInstance()
        db.collection("chats").orderBy("dob", Query.Direction.ASCENDING).addSnapshotListener(object :
            EventListener<QuerySnapshot> {
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
                        chatArrayList.add(dc.document.toObject(Message::class.java))
                    }
                }

                chatAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun sendMessage() {
        val messageId = UUID.randomUUID().toString()

        val message = Message(
            id = messageId,
            message = messageText.text.toString(),
            email = auth.currentUser?.email.toString(),
            nameUser = auth.currentUser?.displayName.toString(),
            imageProfile = auth.currentUser?.photoUrl.toString()
        )

        // DB
        db.collection("chats").document(messageId).set(message)

        messageText.setText("")
    }

    // Menu y toolbar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_secundary, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.nav_salir -> {

                // Cerramos sesión
                auth.signOut()

                // Regresamos a la aterior pantalla
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)

    }
}