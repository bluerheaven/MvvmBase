package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var dataBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        val strArrays = listOf("tab1", "tab2", "tab3", "tab4")
        val fragments = mutableListOf<Fragment>()
        for (str in strArrays) {
            Log.e(tag, "add new fragment, strs = $str")
            fragments.add(NewsListFragment.newInstance())
        }
        dataBinding.viewPager.offscreenPageLimit = strArrays.size
        dataBinding.viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return strArrays[position]
            }
        }
        dataBinding.tabLayout.setupWithViewPager(dataBinding.viewPager)

    }
}