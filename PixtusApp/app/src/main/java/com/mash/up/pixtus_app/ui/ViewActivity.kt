package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import com.example.stepcount.Fragment1
import com.example.stepcount.Fragment2
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.ui.create.CreateStep1Activity
import kotlinx.android.synthetic.main.activity_view.*


class ViewActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var navigation: BottomNavigationView? = null
    private var viewPager: ViewPager? = null
    private val fragment1 = Fragment1()
    private val fragment2 = Fragment2()
    private val fragment3 = Fragment1()
    private val fragment4 = Fragment2()
    private val mOnNavigationItemSelectedListener = object : BottomNavigationView.OnNavigationItemSelectedListener {
        override fun onNavigationItemSelected(p0: MenuItem): Boolean {
            when(p0.getItemId()){
                R.id.navigation_home -> viewPager?.currentItem = 0
                R.id.navigation_exercise -> viewPager?.currentItem = 1
                R.id.navigation_record -> viewPager?.currentItem = 2
                R.id.navigation_food -> {
                    val intent = Intent(this@ViewActivity, MealActivity::class.java)
                    startActivity(intent)
                }
            }
            //viewPager!!.currentItem = p0.order
            return true
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view)

        viewPager = findViewById<ViewPager>(R.id.viewPager)
        viewPager!!.addOnPageChangeListener(this)
        navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation!!.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        viewPager!!.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment? {
                when (position) {
                    0 -> return fragment1
                    1 -> return fragment2
                    2 -> return fragment3
                    3 -> return fragment4
                }
                return null
            }

            override fun getCount(): Int {
                return 4
            }
        }
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        navigation!!.menu.getItem(position).isChecked = true
    }

    override fun onPageScrollStateChanged(state: Int) {
    }
}