package com.mirkamol.android_mvc.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mirkamol.android_mvc.activities.MainActivity
import com.mirkamol.android_mvc.R
import com.mirkamol.android_mvc.model.Post

class PostAdapter(var activity: MainActivity, var items: ArrayList<Post>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_poster_list, parent, false)
        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = items[position]

        if (holder is PostViewHolder) {
            val ll_poster = holder.ll_poster
            val tv_title = holder.tv_title
            val tv_body = holder.tv_body

            tv_title.setText(post.title.toUpperCase())
            tv_body.setText(post.body)


            ll_poster.setOnLongClickListener {
                activity.deletePostDialog(post)
                false
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_poster = view.findViewById<LinearLayout>(R.id.ll_poster)
        val tv_title = view.findViewById<TextView>(R.id.tv_title)
        val tv_body = view.findViewById<TextView>(R.id.tv_body)
    }
}