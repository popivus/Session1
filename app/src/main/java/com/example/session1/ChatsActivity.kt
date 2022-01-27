package com.example.session1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class ChatsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chats)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_menu)

        val chatsFragment = ChatsFragment()
        val profileFragment = ProfileFragment()
        val settingsFragment = SettingsFragment()

        makeCurrentFragment(chatsFragment)

        bottomNav.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.chats -> makeCurrentFragment(chatsFragment)
                R.id.settings -> makeCurrentFragment(settingsFragment)
                R.id.profile -> makeCurrentFragment(profileFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) = supportFragmentManager.beginTransaction().apply {
        replace(R.id.frame_replacer, fragment)
        commit()
    }
}