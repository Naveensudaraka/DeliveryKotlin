package com.example.deliverykotlinapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.deliverykotlinapp.fragments.add.AddFragment

class HomeActivity : AppCompatActivity() {

//    val button = findViewById<Button>(R.id.btnBookTransport)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        button.setOnClickListener {
//            // Open the fragment
//            val fragment = AddFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.addFragment, fragment)
//                .commit()
//        }

        val buttonClick = findViewById<Button>(R.id.btnBookTransport)
        buttonClick.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val buttonClick1 = findViewById<Button>(R.id.btnMyTransportRecord)
        buttonClick1.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }
}
