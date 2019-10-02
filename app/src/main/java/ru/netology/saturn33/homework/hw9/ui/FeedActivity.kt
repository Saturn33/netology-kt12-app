package ru.netology.saturn33.homework.hw9.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ru.netology.saturn33.homework.hw9.R
import ru.netology.saturn33.homework.hw9.Utils
import ru.netology.saturn33.homework.hw9.adapter.PostAdapter
import ru.netology.saturn33.homework.hw9.client.Api
import ru.netology.saturn33.homework.hw9.dto.Post

@KtorExperimentalAPI
class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()
    }

    private fun fetchData() = launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
        }

        var listBasic: MutableList<Post>
        try {
            listBasic = Api.client.get(Api.urlBasic)
            val listAdv = Api.client.get<MutableList<Post>>(Api.urlAdv)
            Utils.injectAds(listBasic, listAdv)
        } catch (e: Exception) {
            listBasic = mutableListOf()
            withContext(Dispatchers.Main) {
                Toast.makeText(
                    this@FeedActivity,
                    "Error: " + e.stackTrace.firstOrNull(),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        withContext(Dispatchers.Main) {
            with(container) {
                layoutManager = LinearLayoutManager(this@FeedActivity)
                adapter = PostAdapter(listBasic)
            }
            progressBar.visibility = View.GONE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}
