package com.example.session1

import android.app.Dialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)

        val singUpBtn = findViewById<Button>(R.id.singUpActivityBtn)
        val singInBtn = findViewById<Button>(R.id.singInBtn)

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
                        else showDialogScreen(this, "Ошибка")
                    }
            }
            else showDialogScreen(this, "Неверный формат данных")
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