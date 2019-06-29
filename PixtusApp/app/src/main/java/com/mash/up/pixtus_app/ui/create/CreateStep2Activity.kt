package com.mash.up.pixtus_app.ui.create

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.ui.MainActivity
import com.mash.up.pixtus_app.utils.showToastMessageString
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_step2.*
import kotlinx.android.synthetic.main.activity_create_step2.tv_next

class CreateStep2Activity : BaseActivity() {

    var weight : String ?= null
    var height : String ?= null
    var sex : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_step2)

        intent.apply {
            weight = this.getStringExtra("weight")
            height = this.getStringExtra("height")
            sex = this.getIntExtra("sex",0)
        }

        tv_next.isSelected = validateStep()
        tv_next.setOnClickListener {
            if (tv_next.isSelected) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
           /*     NetworkCore.getNetworkCore<PixtusApi>().registerUser(
                    hashMapOf(
                        "characterName" to edit_nickname.text.toString()!!,
                        "email" to "yjh54240@naver.com",
                        "gender" to sex!!,
                        "height" to height!!,
                        "weight" to weight!!
                    )
                ).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe({
                        showToastMessageString("로그인 성공")
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }, {
                        it.printStackTrace()
                        showToastMessageString("오류가 발생했습니다.")
                    })*/
            } else {
                showToastMessageString("정보를 다 입력해주세요.")

            }
        }
        Glide.with(this).asGif().load(R.raw.pixtus_login).into(iv_main)

        edit_nickname.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                statusText(validateStep())
            }
        })
    }

    fun statusText(boolean: Boolean) {
        tv_next.isSelected = boolean
    }

    fun validateStep() : Boolean = edit_nickname.text.isNotEmpty()
}
