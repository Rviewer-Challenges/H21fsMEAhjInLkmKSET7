package com.ucielcorp.programmerchat.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ucielcorp.programmerchat.R
import kotlinx.android.synthetic.main.activity_list_of_chats.*

class ListOfChatsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore

    private var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_of_chats)

        //Inicializamos la variable firebaseAuth
        auth = FirebaseAuth.getInstance()

        // Toolbar Config
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        intent.getStringExtra("token")?.let { token = it}
        Log.d("token", token)

        // Boton para ver los miembros de la app
        membersButton.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)

            intent.putExtra("token", token)
            startActivity(intent)
        }

    }

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
