package ru.netology.saturn33.homework.hw6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.saturn33.homework.hw6.adapter.PostAdapter

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        with(container) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(DataGenerator.getInitialPosts())
        }

        newPostsAdd.setOnClickListener {
            try {
                newPostsCount.text.toString().toInt().let {
                    if (it <= 0) return@let
                    val adapter = container.adapter as PostAdapter
                    DataGenerator.getSomePosts(adapter.list, it)
                    adapter.notifyDataSetChanged()
                }
            } catch (e: NumberFormatException) {
            }
            newPostsCount.setText("")
        }
    }
}
