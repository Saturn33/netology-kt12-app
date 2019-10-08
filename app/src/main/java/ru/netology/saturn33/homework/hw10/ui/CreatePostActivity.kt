package ru.netology.saturn33.homework.hw10.ui

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.repositories.Repository
import java.io.IOException

class CreatePostActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var postDialog: ProgressDialog? = null
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postDialog = indeterminateProgressDialog(
            title = "Новый пост",
            message = "Создание поста"
        ).apply {
            setCancelable(false)
            hide()
        }

        setContentView(R.layout.activity_create_post)
        title = getString(R.string.post_creation)

        addPost.setOnClickListener {
            job = launch {
                postDialog?.show()
                try {
                    val result = Repository.createPost(postContent.text.toString())
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
