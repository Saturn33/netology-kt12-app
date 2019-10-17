package ru.netology.saturn33.homework.hw12.ui

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_create_post.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw12.R
import ru.netology.saturn33.homework.hw12.dto.AttachmentModel
import ru.netology.saturn33.homework.hw12.repositories.Repository
import java.io.IOException

class CreatePostActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    private val REQUEST_IMAGE_CAPTURE = 1
    private var postDialog: ProgressDialog? = null
    private var uploadDialog: ProgressDialog? = null
    private var attachmentModel: AttachmentModel? = null
    private var job: Job? = null

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        }
        else {
            return super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        postDialog = indeterminateProgressDialog(
            title = getString(R.string.new_post),
            message = getString(R.string.new_post_creating)
        ).apply {
            setCancelable(false)
            hide()
        }
        uploadDialog = indeterminateProgressDialog(
            title = getString(R.string.uploading_title),
            message = getString(R.string.uploading_content)
        ).apply {
            setCancelable(false)
            hide()
        }

        setContentView(R.layout.activity_create_post)
        title = getString(R.string.post_creation)

        addPhoto.setOnClickListener {
            dispatchTakePictureIntent()
        }

        addPost.setOnClickListener {
            job = launch {
                postDialog?.show()
                try {
                    val result = Repository.createPost(postContent.text.toString(), attachmentModel)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK)
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap?
                    imageBitmap?.let {
                        launch {
                            uploadDialog?.show()
                            try {
                                val imageUploadResult = Repository.upload(it)
                                if (imageUploadResult.isSuccessful) {
                                    imageUploaded()
                                    attachmentModel = imageUploadResult.body()
                                }
                                else {
                                    toast(getString(R.string.error_uploading))
                                }
                            } catch (e: IOException) {
                                toast(getString(R.string.error_uploading))
                            }
                            uploadDialog?.hide()
                        }
                    }
                }
            }
    }

    private fun imageUploaded() {
        addPhoto.setImageResource(R.drawable.ic_add_a_photo_inactive_24dp)
        addPhotoDone.visibility = View.VISIBLE
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
        postDialog?.dismiss()
        uploadDialog?.dismiss()
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }
        }
    }
}
