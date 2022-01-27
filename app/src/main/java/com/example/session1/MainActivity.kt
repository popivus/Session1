package com.example.session1

import android.app.Dialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.*
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val singUpBtn = findViewById<Button>(R.id.singUpActivityBtn)
        val singInBtn = findViewById<Button>(R.id.singInBtn)
        val google = findViewById<ImageView>(R.id.googleAuth)

        singUpBtn.setOnClickListener {
            startActivity(Intent(this, SingUpActivity::class.java))
        }

        singInBtn.setOnClickListener {
            if (email.text.toString().isEmail() && password.text.toString().isNotEmpty()){
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful){
                            startActivity(Intent(this, ChatsActivity::class.java))
                            finish()
                        }
                        else showDialogScreen(this, task.exception.toString())
                    }
            }
            else showDialogScreen(this, "Неверный формат данных")
        }

        google.setOnClickListener {
            signIn()
        }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, 9001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 9001) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                showDialogScreen(this, e.toString())
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    startActivity(Intent(this, ChatsActivity::class.java))
                    finish()
                    Toast.makeText(this, "Авторизация прошла успешна", Toast.LENGTH_SHORT).show()
                } else showDialogScreen(this, task.exception.toString())
            }
    }
}

fun CharSequence?.isEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun showDialogScreen(context: Context, text: String){
    val dialog = Dialog(context)
    dialog.setContentView(R.layout.dialog_layout)
    val textView = dialog.findViewById<TextView>(R.id.textDialog)
    textView.text = text
    dialog.show()
}