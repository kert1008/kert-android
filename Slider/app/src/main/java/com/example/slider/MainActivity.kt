package com.example.slider

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.activity_main.*

private const val NUM_PAGES = 2

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewPager = findViewById(R.id.view_pager)

        val pagerAdapter = ScreenSlidePagerAdapter(this, (1..100).shuffled().first().toString())
        viewPager.adapter = pagerAdapter
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL

        // Specify fragment which being shown as initial
        viewPager.currentItem = 1

        button1.setOnClickListener {
            Log.d("MainActivityTag", "button clicked")
            val intent = Intent(this,this::class.java)
            startActivity(intent)
            finish()
        }
    }

    private inner class ScreenSlidePagerAdapter(fa: FragmentActivity, content: String) :
        FragmentStateAdapter(fa) {
        var showContent: String = content
        override fun getItemCount(): Int = NUM_PAGES
        override fun createFragment(position: Int): Fragment {
            return ScreenSlidePageFragment(position, showContent)
        }
    }
}