package ru.netology.saturn33.homework.hw12.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw12.*
import ru.netology.saturn33.homework.hw12.adapter.PostAdapter
import ru.netology.saturn33.homework.hw12.dto.PostModel
import ru.netology.saturn33.homework.hw12.repositories.Repository
import java.io.IOException
import java.util.concurrent.TimeUnit

class FeedActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private var feedDialog: ProgressDialog? = null
    private var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        scheduleJob()

        fab.setOnClickListener {
            startActivity<CreatePostActivity>()
        }

        requestToken()
    }

    private fun requestToken() {
        with(GoogleApiAvailability.getInstance()) {
            val code = isGooglePlayServicesAvailable(this@FeedActivity)
            if (code == ConnectionResult.SUCCESS) {
                return@with
            }

            if (isUserResolvableError(code)) {
                getErrorDialog(this@FeedActivity, code, 9000).show()
                return
            }

            longToast(getString(R.string.googleplay_services_unavailable))
            return
        }

        FirebaseInstanceId.getInstance().instanceId.addOnSuccessListener {
            launch {
                println(it.token)
                Repository.registerPushToken(it.token)
            }
        }
    }

    private fun scheduleJob() {
        val checkWork = PeriodicWorkRequestBuilder<UserNotHereWorker>(
            SHOW_NOTIFICATION_AFTER_UNVISITED_MS, TimeUnit.MILLISECONDS
        )
            .build()
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                "user_present_work",
                ExistingPeriodicWorkPolicy.KEEP,
                checkWork
            )
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

    override fun onStop() {
        super.onStop()
        feedDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        if (Utils.isFirstTime(this)) {
            NotificationHelper.comeBackNotification(this)
        }
        Utils.setLastVisitTime(this, System.currentTimeMillis())
    }
}
