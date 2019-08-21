package com.mash.up.pixtus_app.ui.create

import android.content.Intent
import android.os.Bundle
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.ui.ViewActivity
import kotlinx.android.synthetic.main.activity_create_step3.*

class CreateStep3Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_step3)
        tv_name.text = intent.getStringExtra("name")

        Glide.with(this).asGif().load(R.raw.pixtus_start).into(iv_gif2)

        /*
        tv_complete.setText(
            Html.fromHtml(
                "당신의 픽터스 " + "<font color=\"#5dbb96\"><b><b>" + name +
                        "</b></b></font>" + " 이" + "<br>" + "생성되었습니다"
            )
        )
        */

        complete_btn.setOnClickListener {
            val intent = Intent(this, ViewActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}