package ru.netology.saturn33.homework.hw5

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.saturn33.homework.hw5.adapter.PostAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(DataGenerator.getInitialPosts())
        }

        newPostsAdd.setOnClickListener {
            newPostsCount.text.toString().toInt().let {
                if (it <= 0) return@let
                newPostsCount.setText("")
                val adapter = container.adapter as PostAdapter
                DataGenerator.getSomePosts(adapter.list, it)
                adapter.notifyDataSetChanged()
            }
        }
    }
}
