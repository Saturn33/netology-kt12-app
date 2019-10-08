package ru.netology.saturn33.homework.hw10.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_repost.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.repositories.Repository
import java.io.IOException

class CreateRepostActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var postDialog: ProgressDialog? = null
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postDialog = indeterminateProgressDialog(
            title = "Новый репост",
            message = "Создание репоста"
        ).apply {
            setCancelable(false)
            hide()
        }

        setContentView(R.layout.activity_create_repost)
        val postId = intent.getLongExtra("postId", 0)
        title = "Создание репоста $postId"


        addPost.setOnClickListener {
            job = launch {
                postDialog?.show()
                try {
                    val result = Repository.createRepost(postId, postContent.text.toString())
                    if (result.isSuccessful)
                    {
                        handleSuccessResult()
                    }
                    else
                    {
                        handleErrorResult()
                    }
                }
                catch (e: IOException) {
                    handleErrorResult()
                }
                finally {
                    postDialog?.hide()
                }
            }
        }
    }

    private fun handleErrorResult() {
        toast(getString(R.string.post_creation_error))
    }

    private fun handleSuccessResult() {
        toast(getString(R.string.post_creation_success))
        finish()
    }


    override fun onDestroy() {
        super.onDestroy()
        job?.cancel()
        postDialog?.hide()
    }
}
