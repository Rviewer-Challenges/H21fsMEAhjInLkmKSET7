package com.ucielcorp.chatfirenase.Views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.ucielcorp.chatfirenase.R

class LoginActivity : AppCompatActivity() {

    companion object{
        private const val RC_SIGN_IN = 120
    }

    // Varibles para la autenticación
    private lateinit var auth : FirebaseAuth
    private lateinit var googleSingInClient : GoogleSignInClient
    private lateinit var db : FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Firebase Auth Instance
        auth = FirebaseAuth.getInstance()

        // Firebase Firestore Instance
        db = FirebaseFirestore.getInstance()

        checkUser()
        loginUser()
    }

    private fun checkUser(){
        // Si el usuario ya logeado esta variable va a existir
        val currentUser = auth.currentUser

        // Si el usuario ya esta logea directamente lo enviamos a la pantalla de chat
        if(currentUser != null){
            val intent = Intent(this, RoomChatActicity::class.java)
            startActivity(intent)

            // Cerramos la actividad
            finish()
        }
    }

    /**===/ Función principal de inicio de sesión /===**/
    private fun loginUser(){

        // Realizamos la configuración de inicio de sesión con google
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSingInClient = GoogleSignIn.getClient(this, gso)

        // Damos la función al boton de inicio de sesión de google
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener { singIn() }
    }

    /**===/ Función qué se utiliza para iniciar sesión al momento de hacer click en el /===**/
    private fun singIn(){
        val signInIntent =  googleSingInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    /**===/ Designamos que al momento de realizar la petición de inicio de sesión, ejecutamos la tarea y verificamos que sea exitosa /===**/
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            val exception = task.exception
            if(task.isSuccessful){
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d("SignInActicity", "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w("SignInActivity", "Google sign in failed", e)
                }
            } else {
                Log.w("SignInActivity", exception.toString())
            }
        }
    }

    /**===/

     * Es especificamente la encarda de realizar la autenticación y por la cual, nos podemos mover a otras activities
     * Adicionalmente podemos enviar datos del usuario autenticado a la otra activity

    /===**/
    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("SignInActivity", "signInWithCredential:success")

                    // Datos del usuario
                    //val currentUser = auth.currentUser
                    //val user = User(
                    //    name = currentUser!!.displayName.toString(),
                    //    image = currentUser.photoUrl.toString(),
                    //    email = currentUser.email.toString(),
                    //    token = currentUser.uid)

                    val intent = Intent(this, RoomChatActicity::class.java)

                    // Almacenamos los datos dentro de la base de datos
                    //db.collection("user").document(currentUser.email.toString()).set(user)

                    startActivity(intent)
                    finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("SignInActivity", "signInWithCredential:failure", task.exception)
                }
            }
    }
}