package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentNewsBinding


class NewsListFragment : Fragment() {

    companion object {
        fun newInstance(): NewsListFragment {
            return NewsListFragment()
        }
    }

    lateinit var viewDataBinding: FragmentNewsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_news, container, false)
        viewDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_news, container, false)

        return root
    }

}