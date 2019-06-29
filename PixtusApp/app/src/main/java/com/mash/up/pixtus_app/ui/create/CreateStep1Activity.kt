package com.mash.up.pixtus_app.ui.create

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Editable
import android.text.TextWatcher
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.utils.showToastMessageString

import kotlinx.android.synthetic.main.activity_create_step.*
import kotlinx.android.synthetic.main.activity_create_step.edit_height
import kotlinx.android.synthetic.main.activity_create_step.edit_weight
import kotlinx.android.synthetic.main.fragment_step1.*

class CreateStep1Activity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_step)


        tv_next.isSelected = validateStep()

        tv_next.setOnClickListener {
            if (tv_next.isSelected){
                val intent = Intent(this, CreateStep2Activity::class.java)
                intent.putExtra("weight",edit_weight.text.toString())
                intent.putExtra("height",edit_height.text.toString())
                intent.putExtra("sex",checkSex())
                startActivity(intent)
                finish()
            } else {
                showToastMessageString("정보를 다 입력해주세요.")
            }
        }


        layout_male.setOnClickListener {
            male()
            statusText(validateStep())
        }

        layout_female.setOnClickListener {
            female()
            statusText(validateStep())
        }

        edit_height.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                statusText(validateStep())
            }
        })


        edit_weight.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                statusText(validateStep())
            }

        })

    }


    fun validateStep() : Boolean = edit_height.text.isNotEmpty() && (layout_male.isSelected || layout_female.isSelected)

    fun statusText(boolean: Boolean) {
        tv_next.isSelected = boolean
    }

    fun checkSex() : Int{
        return when {
            layout_male.isSelected -> 0
            layout_female.isSelected -> 1
            else -> 0
        }
    }
    fun male(){
        tv_male.isSelected = true
        layout_male.isSelected = true
        view_male.isSelected = true

        tv_female.isSelected = false
        layout_female.isSelected = false
        view_female.isSelected = false
    }

    fun female(){
        tv_female.isSelected = true
        layout_female.isSelected = true
        view_female.isSelected = true

        tv_male.isSelected = false
        layout_male.isSelected = false
        view_male.isSelected = false
    }

}


