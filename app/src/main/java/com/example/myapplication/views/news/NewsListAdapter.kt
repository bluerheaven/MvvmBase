package com.example.myapplication.views.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.network.NewsListBean


class NewsListAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_PICTURE_TITLE = 1
    private val VIEW_TYPE_TITLE = 2
    val mItems = mutableListOf<NewsListBean.Contentlist>()

    fun setItems(items: NewsListBean.Contentlist) {
//        mItems.addAll(items)
    }

    override fun getItemViewType(position: Int): Int {
        if (mItems[position].imageurls != null && mItems[position].imageurls.size > 1) {
            return VIEW_TYPE_PICTURE_TITLE
        }else {
            return VIEW_TYPE_TITLE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_PICTURE_TITLE) {
            PicViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.picture_title_view, parent, false))
        }else {
            TitleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.title_view, parent, false))
        }
    }


    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mItems[position]

    }

    class PicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.item_image)
        val tv: TextView = itemView.findViewById(R.id.item_file_name)
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.item_file_name)
    }

}