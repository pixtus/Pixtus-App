package com.mash.up.pixtus_app.ui

import android.os.Bundle
import com.mash.up.pixtus_app.R
import android.content.Intent
import android.os.Handler
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1500)

    }
}
