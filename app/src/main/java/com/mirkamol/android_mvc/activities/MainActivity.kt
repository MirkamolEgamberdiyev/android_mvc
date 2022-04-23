package com.mirkamol.android_mvc.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mirkamol.android_mvc.R
import com.mirkamol.android_mvc.adapter.PostAdapter
import com.mirkamol.android_mvc.model.Post
import com.mirkamol.android_mvc.network.RetrofitHttp
import com.mirkamol.android_mvc.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView:RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()

    }

    private fun initViews() {
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.setLayoutManager(GridLayoutManager(this, 1))
        apiPostList()
    }

    fun refreshAdapter(posters: ArrayList<Post>) {
        val adapter = PostAdapter(this, posters)
        recyclerView.setAdapter(adapter)
    }

    private fun apiPostList() {
        RetrofitHttp.postService.listPost().enqueue(object : Callback<ArrayList<Post>> {
            override fun onResponse(
                call: Call<ArrayList<Post>>,
                response: Response<ArrayList<Post>>
            ) {
                refreshAdapter(response.body()!!)
                Log.d("TAG", "onResponse:${response.body()} ")
            }

            override fun onFailure(call: Call<ArrayList<Post>>, t: Throwable) {

            }
        })
    }

    private fun apiPostDelete(post: Post) {
        RetrofitHttp.postService.deletePost(post.id).enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                apiPostList()

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.d("TAG", "onResponse:${t.localizedMessage.toString()} ")
            }
        })
    }



    fun deletePostDialog(post: Post) {
        val title = "Delete"
        val body = "Do you want to delete?"
        Utils.customDialog(this, title, body, object : Utils.DialogListener {
            override fun onPositiveClick() {
                apiPostDelete(post)
            }

            override fun onNegativeClick() {

            }
        })
    }


}