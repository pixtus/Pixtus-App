package com.mash.up.pixtus_app.ui

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.example.stepcount.MainFragment
import com.example.stepcount.WorkoutFragment
import com.mash.up.pixtus_app.R
import com.mash.up.pixtus_app.ui.history.fragment.HistoryFragment


class ViewActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {

    private var navigation: BottomNavigationView? = null
    private var viewPager: ViewPager? = null
    private val fragment1 = MainFragment()
    private val fragment2 = WorkoutFragment()
    private val fragment3 = HistoryFragment.newInstance()
    private val fragment4 = MainFragment()
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