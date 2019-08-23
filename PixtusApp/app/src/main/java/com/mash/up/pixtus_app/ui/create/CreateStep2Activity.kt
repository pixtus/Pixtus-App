package com.mash.up.pixtus_app.ui.create

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.core.CreateAcountApi
import com.mash.up.pixtus_app.core.NetworkCore
import com.mash.up.pixtus_app.core.PixtusApi
import com.mash.up.pixtus_app.ui.MainActivity
import com.mash.up.pixtus_app.ui.ViewActivity
import com.mash.up.pixtus_app.ui.create.data.CreateAcoountRequest
import com.mash.up.pixtus_app.ui.login.PostLoginRequest
import com.mash.up.pixtus_app.utils.showToastMessageString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_create_step.*
import kotlinx.android.synthetic.main.activity_create_step2.*
import kotlinx.android.synthetic.main.activity_create_step2.tv_next
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class CreateStep2Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_step2)

        tv_next.isSelected = validateStep()

        tv_next.setOnClickListener {
            if (tv_next.isSelected) {
                var uid = intent.getStringExtra("uid")
                var email = intent.getStringExtra("email")
                var weight = intent.getStringExtra("weight")
                var height = intent.getStringExtra("height")
                var sex = intent.getStringExtra("sex")
                var name = intent.getStringExtra("name")
                // var name = intent.getStringExtra("name")
                postCreateAccount(uid, email, name, edit_nickname.text.toString(), height, weight, sex)
            } else
                showToastMessageString("정보를 다 입력해주세요.")
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

    fun validateStep(): Boolean = edit_nickname.text.isNotEmpty()

    fun postCreateAccount(
        uid: String,
        email: String,
        name: String,
        characterName: String,
        height: String,
        weight: String,
        gender: String
    ) {

        NetworkCore.getNetworkCore<CreateAcountApi>()
            .postCreateAccount(
                "application/json",
                CreateAcoountRequest(characterName, email, gender, height.toInt(), name, uid, weight.toInt())
            ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                val intent = Intent(this, CreateStep3Activity::class.java)
                intent.putExtra("name", edit_nickname.text.toString())
                startActivity(intent)
                finish()
            }, {
                Log.e("CreateStep2Act err", Log.getStackTraceString(it))
            })
    }

}
