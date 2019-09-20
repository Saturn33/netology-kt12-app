package ru.netology.saturn33.homework.hw6

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import ru.netology.saturn33.homework.hw6.adapter.PostAdapter
import ru.netology.saturn33.homework.hw6.client.Api
import ru.netology.saturn33.homework.hw6.dto.Post

@KtorExperimentalAPI
class MainActivity : AppCompatActivity(), CoroutineScope by MainScope() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchData()
    }

    private fun fetchData() = launch(Dispatchers.IO) {
        withContext(Dispatchers.Main) {
            progressBar.visibility = View.VISIBLE
        }

        val listBasic = Api.client.get<MutableList<Post>>(Api.urlBasic)
        val listAdv = Api.client.get<MutableList<Post>>(Api.urlAdv)
        Utils.injectAds(listBasic, listAdv)

        withContext(Dispatchers.Main) {
            with(container) {
                layoutManager = LinearLayoutManager(this@MainActivity)
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
