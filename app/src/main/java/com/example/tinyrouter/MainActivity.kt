package com.example.tinyrouter

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tinyrouter.api.TinyRouter
import com.tinyrouter.inter.IRouterCallback
import com.tinyrouter.inter.IRouterInterceptor
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setGlobalListener()
        initView()
    }

    fun setGlobalListener() {
        // 全局拦截器
        TinyRouter.addGlobalNavigatorInterceptor(object : IRouterInterceptor {
            override fun next(uri: Uri?): Boolean {
                if (uri.toString().contains("abc")) {
                    Toast.makeText(application, "被拦截了", Toast.LENGTH_SHORT).show()
                    return false
                }
                return true
            }
        })

        // 全局监听
        TinyRouter.setGlobalNavigatorCallback(object : IRouterCallback {
            override fun onSuccess(uri: Uri) {
                Log.e(TAG, "全局监听 onSuccess ：$uri")
            }

            override fun onFail(uri: Uri, e: Exception) {
                Log.e(TAG, "全局监听 onFail ：$uri  || error：${e.message}")
            }

        })
    }

    fun initView() {
        button_login.setOnClickListener {
            TinyRouter.navigator(this, "myrouter://login?fromwhere=app")
        }

        button_home.setOnClickListener {
            TinyRouter.navigator(this, "myrouter://home")
        }

        button_call.setOnClickListener {
            TinyRouter.navigator(this, "myrouter://tel?number=18211125210")
        }

        button_fail.setOnClickListener {
            // 不存在的路由
            TinyRouter.navigator(this, "myrouter://home/error", object : IRouterCallback {
                override fun onSuccess(uri: Uri) {
                    Toast.makeText(application, "success:$uri", Toast.LENGTH_SHORT).show()
                }

                override fun onFail(uri: Uri, e: Exception) {
                    Toast.makeText(application, "fail:" + e.message, Toast.LENGTH_SHORT).show()
                }
            })
        }

        button_interceptor.setOnClickListener {
            // 会被全局拦截器拦截
            TinyRouter.navigator(this, "myrouter://abc")
        }
    }
}