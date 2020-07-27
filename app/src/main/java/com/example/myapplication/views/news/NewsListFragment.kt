package com.example.myapplication.views.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentNewsBinding
import com.example.myapplication.network.CommonCallback
import com.example.myapplication.network.NetWorkApi
import com.example.myapplication.network.NewsListBean


class NewsListFragment : Fragment() {

    companion object {
        private const val BUNDLE_KEY_PARAM_CHANNEL_ID = "bundle_key_param_channel_id"
        private const val BUNDLE_KEY_PARAM_CHANNEL_NAME = "bundle_key_param_channel_name"

        fun newInstance(channelId: String, channelName: String): NewsListFragment {
            val fragment = NewsListFragment()
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_ID, channelId)
            bundle.putString(BUNDLE_KEY_PARAM_CHANNEL_NAME, channelName)
            fragment.arguments = bundle
            return fragment
        }
    }

    lateinit var viewDataBinding: FragmentNewsBinding
    lateinit var adapter: NewsListAdapter
    var mChannelId = ""
    var mChannelName = ""


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_news, container, false)
        viewDataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)
        initParameters()
        adapter = NewsListAdapter()
        viewDataBinding.recyclerView.adapter = adapter
        loadData()
        return root
    }

    private fun initParameters() {
        if (arguments != null) {
            mChannelId = arguments!!.getString(BUNDLE_KEY_PARAM_CHANNEL_ID)!!
            mChannelName = arguments!!.getString(BUNDLE_KEY_PARAM_CHANNEL_NAME)!!
        }
    }

    private fun loadData() {
        NetWorkApi.getInstance()
            .getNewsList(mChannelId, mChannelName, 1, object : CommonCallback<NewsListBean> {
                override fun onSuccess(data: NewsListBean) {
                    adapter.setItems(data.Contentlist())
                }

                override fun onError(error: String?) {
                }
            })
    }

}