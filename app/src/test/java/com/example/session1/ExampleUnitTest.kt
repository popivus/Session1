package com.example.session1

import android.content.Intent
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun registrationAccepted() {
        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword("example123@gmail.com", "123456789").addOnCompleteListener { task ->
            assertEquals(true, task.isSuccessful)
        }
    }
}