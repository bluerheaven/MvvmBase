package com.example.myapplication

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.loadsir.EmptyCallback
import com.example.myapplication.loadsir.ErrorCallback
import com.example.myapplication.loadsir.LoadingCallback
import com.example.myapplication.network.CommonCallback
import com.example.myapplication.network.NetWorkApi
import com.example.myapplication.network.NewsChannelsBean
import com.example.myapplication.views.news.NewsListFragment
import com.google.android.material.tabs.TabLayout
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir


class MainActivity : AppCompatActivity() {
    private val tag = "MainActivity"
    private lateinit var dataBinding: ActivityMainBinding
    private lateinit var loadSir: LoadService<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        loadSir = LoadSir.getDefault().register(this) {
            loadData()
        }
        loadData()
    }

    private fun loadViews(channelsBean: NewsChannelsBean) {
        val size = channelsBean.showapiResBody.channelList.size
        if (channelsBean.showapiResBody.channelList.size == 0) {
            loadSir.showCallback(EmptyCallback::class.java)
            return
        }

        val fragments = mutableListOf<Fragment>()
        for (bean in channelsBean.showapiResBody.channelList) {
            fragments.add(NewsListFragment.newInstance(bean.channelId, bean.name))
        }
        dataBinding.viewPager.offscreenPageLimit = 2
        dataBinding.viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return channelsBean.showapiResBody.channelList[position].name
            }
        }
        dataBinding.tabLayout.tabMode = TabLayout.MODE_SCROLLABLE
        dataBinding.tabLayout.setupWithViewPager(dataBinding.viewPager)

    }

    private fun loadData() {
        loadSir.showCallback(LoadingCallback::class.java)
        NetWorkApi.getInstance().getChannels(object : CommonCallback<NewsChannelsBean> {
            override fun onSuccess(data: NewsChannelsBean) {
                Log.e(tag, "get channels data NewsChannelsBean = $data")
                loadSir.showSuccess()
                loadViews(data)
            }

            override fun onError(error: String?) {
                Log.e(tag, "get channels data onError = $error")
                loadSir.showCallback(ErrorCallback::class.java)
            }
        })
    }


}