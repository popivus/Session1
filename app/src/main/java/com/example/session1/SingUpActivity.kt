package com.example.session1

import android.content.Intent
import android.net.Credentials
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class SingUpActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        auth = FirebaseAuth.getInstance()

        val cancelBtn = findViewById<Button>(R.id.cancelBtn)
        val singUpBtn = findViewById<Button>(R.id.singUpBtn)

        val name = findViewById<EditText>(R.id.nameReg)
        val email = findViewById<EditText>(R.id.emailReg)
        val password = findViewById<EditText>(R.id.passwordReg)
        val repassword = findViewById<EditText>(R.id.repasswordReg)


        cancelBtn.setOnClickListener {
            finish()
        }

        singUpBtn.setOnClickListener {
            if (email.text.toString().isEmail() && password.text.isNotEmpty() && repassword.text.isNotEmpty() && name.text.isNotEmpty()){
                if (password.text.toString() == repassword.text.toString()){
                    auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString()).addOnCompleteListener(this) { task ->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Регистрация прошла успешно", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, ChatsActivity::class.java))
                            finish()
                        }
                        else showDialogScreen(this, task.exception.toString())
                    }
                }
                else showDialogScreen(this, "Пароли не совпадают")
            }
            else showDialogScreen(this, "Неверный формат данных")
        }
    }
}