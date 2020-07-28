package com.example.myapplication.views.news

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.network.NewsListBean


class NewsListAdapter(private val context: Activity) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_PICTURE_TITLE = 1
    private val VIEW_TYPE_TITLE = 2
    val mItems = mutableListOf<NewsListBean.Contentlist>()

    fun setItems(items: List<NewsListBean.Contentlist>) {
        mItems.addAll(items)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mItems[position].imageurls != null && mItems[position].imageurls.size > 1) {
                VIEW_TYPE_PICTURE_TITLE
        }else {
            VIEW_TYPE_TITLE
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
        if (holder is PicViewHolder) {
            Glide.with(context).load(item.imageurls[0]).into(holder.iv)
            holder.tv.text = item.title
        }else if (holder is TitleViewHolder) {
            holder.tv.text = item.title
        }
    }

    class PicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv: ImageView = itemView.findViewById(R.id.item_image)
        val tv: TextView = itemView.findViewById(R.id.item_file_name)
    }

    class TitleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv: TextView = itemView.findViewById(R.id.item_file_name)
    }

}