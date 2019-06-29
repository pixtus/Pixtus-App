package com.mash.up.pixtus_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.mash.up.pixtus_app.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
