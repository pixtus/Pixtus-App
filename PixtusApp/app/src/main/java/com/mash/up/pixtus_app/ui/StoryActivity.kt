package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_story.*

class StoryActivity : BaseActivity() {

    var story_string: Array<String> = arrayOf("픽터스는 꽃을 피우기 위해 무던히 노력했지만\n혼자서는 쉽지 않았답니다.",
        "외로운 픽터스 에게는 함께 노력해줄 친구가 필요했어요.", "\"어이, 거기!!\"", "\"그래, 거기 스마트폰 밖에 있는 당신\"",
        "\"나와 함께 열심히 꽃 피워 보겠어?\"")
    var current_step = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        Glide.with(this).asGif().load(R.raw.pixtus_story01).into(imageView3)

        story_next_btn.setOnClickListener {
            current_step++
            if(current_step < 5) {
                tv_story.text = story_string[current_step]
                when(current_step){
                    0->Glide.with(this).asGif().load(R.raw.pixtus_story02).into(imageView3)
                    1->Glide.with(this).asGif().load(R.raw.pixtus_story03).into(imageView3)
                    2->{
                        tv_story.setTextColor(Color.parseColor("#5dbb96"))
                        Glide.with(this).asGif().load(R.raw.pixtus_story04).into(imageView3)
                    }
                    4->{
                        tv_narration.text = "PIXTUS"
                        tv_together.setTextColor(Color.parseColor("#5dbb96"))
                    }
                }
            }
            else if (current_step == 5) {
                moveNextPage()
            }
        }

        skip_btn.setOnClickListener {
            tv_narration.text = "PIXTUS"
            tv_together.setTextColor(Color.parseColor("#5dbb96"))
            tv_story.setTextColor(Color.parseColor("#5dbb96"))
            tv_story.text = story_string[4]
            Glide.with(this).asGif().load(R.raw.pixtus_story04).into(imageView3)
            current_step = 5
        }

        tv_together.setOnClickListener() {
            moveNextPage()
        }
    }

    fun moveNextPage() {
        val intent = Intent(this, CreateStep1Activity::class.java)
        startActivity(intent)
        finish()
    }
}