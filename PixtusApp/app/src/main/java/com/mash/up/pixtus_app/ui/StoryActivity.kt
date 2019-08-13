package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import retrofit2.http.HEAD

import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.base.BaseActivity
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_story.*
import kotlinx.android.synthetic.main.activity_story.view.*
import kotlinx.android.synthetic.main.item_workout.*

class StoryActivity : BaseActivity() {

    var story_string: Array<String> = arrayOf(
        "픽터스는 꽃을 피우기 위해 무던히 노력했지만\n...",
        "픽터스는 꽃을 피우기 위해 무던히 노력했지만\n혼자서는 쉽지 않았답니다.",
        "외로운 픽터스 에게는 함께 노력해줄 친구가 필요했어요.", "\"어이, 거기!!\"", "\"그래, 거기 스마트폰 밖에 있는 당신\"",
        "\"나와 함께 열심히 꽃 피워 보겠어?\""
    )
    var current_step = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_story)

        //Glide.with(this).asGif().load(R.raw.pixtus_story01).into(imageView3)
        val animationView = findViewById<LottieAnimationView>(R.id.animationView)

        animationView.setAnimation("pixtus_story_01.json")
        animationView.loop(true)
        animationView.playAnimation()

        story_next_btn.setOnClickListener {
            current_step++
            if (current_step < 6) {
                tv_story.text = story_string[current_step]
                when (current_step) {
                    0 -> {
                        animationView.setAnimation("pixtus_story_02.json")
                        animationView.playAnimation()
                    }
                    2 -> {
                        animationView.setAnimation("pixtus_story_03.json")
                        animationView.playAnimation()
                    }
                    3 -> {
                        tv_story.setTextColor(Color.parseColor("#5dbb96"))
                        animationView.setAnimation("pixtus_story_04.json")
                        animationView.playAnimation()
                        tv_narration.text = "PIXTUS"
                    }

                    5->{
                        tv_together.setVisibility(View.VISIBLE)
                    }
                }
            } else if (current_step == 6) {
                moveNextPage()
            }
        }

        skip_btn.setOnClickListener {
            tv_narration.text = "PIXTUS"
            tv_together.setVisibility(View.VISIBLE)
            tv_story.setTextColor(Color.parseColor("#5dbb96"))
            tv_story.text = story_string[5]
            animationView.setAnimation("pixtus_story_04.json")
            animationView.playAnimation()
            current_step = 5
        }

        tv_together.setOnClickListener() {
            if (current_step == 5)
                moveNextPage()
        }
    }

    fun moveNextPage() {
        val intent = Intent(this, CreateStep1Activity::class.java)
        startActivity(intent)
        finish()
    }
}