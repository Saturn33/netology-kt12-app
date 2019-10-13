package ru.netology.saturn33.homework.hw11.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw11.NotificationHelper
import ru.netology.saturn33.homework.hw11.R
import ru.netology.saturn33.homework.hw11.Utils
import ru.netology.saturn33.homework.hw11.adapter.PostAdapter
import ru.netology.saturn33.homework.hw11.dto.PostModel
import ru.netology.saturn33.homework.hw11.repositories.Repository
import java.io.IOException

class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var feedDialog: ProgressDialog? = null
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            startActivity<CreatePostActivity>()
        }
    }

    override fun onStart() {
        super.onStart()

        feedDialog = indeterminateProgressDialog(
            title = getString(R.string.feed),
            message = getString(R.string.fetching_feed)
        ).apply {
            setCancelable(false)
            hide()
        }

        job = launch {
            feedDialog?.show()
            try {
                val result = Repository.getRecentPosts()
                if (result.isSuccessful) {
                    with(container) {
                        layoutManager = LinearLayoutManager(this@FeedActivity)
                        val list = result.body() ?: mutableListOf<PostModel>()
                        adapter = PostAdapter(list)
                    }
                }
            } catch (e: IOException) {
                toast(getString(R.string.feed_fetch_error))
            }
            feedDialog?.hide()
        }

        swipeContainer.setOnRefreshListener {
            if (container.adapter is PostAdapter) {
                GlobalScope.launch(Dispatchers.Main) {
                    try {
                        val response =
                            Repository.getPostsAfter(if ((container.adapter as PostAdapter).list.isEmpty()) 0 else (container.adapter as PostAdapter).list.first().id)

                        if (response.isSuccessful) {
                            val newItems = response.body()!!
                            if (newItems.size > 0) {
                                (container.adapter as PostAdapter).list.addAll(0, newItems)
                                (container.adapter as PostAdapter).notifyItemRangeInserted(
                                    0,
                                    newItems.size
                                )
                            }
                        } else {
                            toast(getString(R.string.update_error))
                        }
                    } catch (e: IOException) {
                        toast(getString(R.string.feed_fetch_error))
                    }
                    swipeContainer.isRefreshing = false
                }
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                Utils.removeUserAuth(this@FeedActivity)
                startActivity<AuthActivity>()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        feedDialog?.dismiss()
        if (Utils.isFirstTime(this)) {
            NotificationHelper.comeBackNotification(this)
            Utils.setNotFirstTime(this)
        }
    }
}
