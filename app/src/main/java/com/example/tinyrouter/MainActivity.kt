package com.example.tinyrouter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tinyrouter.api.TinyRouter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_login.setOnClickListener {
            TinyRouter.getInstance().navigation("myrouter://home")
            //TinyRouter.getInstance().navigation("myrouter://home2")
            //startActivity(Intent(this@MainActivity, LoginActivity::class.java))
        }

        button_home.setOnClickListener {
            //startActivity(Intent(this@MainActivity, HomeActivity::class.java))
            TinyRouter.getInstance().navigation("myrouter://login")
        }


        button_call.setOnClickListener {

        }
    }
}