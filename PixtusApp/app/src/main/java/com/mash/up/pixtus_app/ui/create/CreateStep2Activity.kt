package com.mash.up.pixtus_app.ui.create

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.utils.showToastMessageString
import kotlinx.android.synthetic.main.activity_create_step2.*
import kotlinx.android.synthetic.main.activity_create_step2.tv_next

class CreateStep2Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_step2)

        tv_next.isSelected = validateStep()

        tv_next.setOnClickListener {
            if (tv_next.isSelected){
                showToastMessageString("굿.")
            } else
                showToastMessageString("정보를 다 입력해주세요.")
        }


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