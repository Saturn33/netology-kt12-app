package ru.netology.saturn33.homework.hw9.ui

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.util.KtorExperimentalAPI
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import ru.netology.saturn33.homework.hw9.API_SHARED_FILE
import ru.netology.saturn33.homework.hw9.AUTHENTICATED_SHARED_KEY
import ru.netology.saturn33.homework.hw9.R
import ru.netology.saturn33.homework.hw9.repositories.Repository

@KtorExperimentalAPI
class AuthActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    @Suppress("DEPRECATION")
    val authDialog =
        indeterminateProgressDialog(
            message = getString(R.string.auth_checking_data),
            title = getString(R.string.auth_title)
        ) {
            setCancelable(false)
            show()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isAuthenticated()) {
            startActivity<FeedActivity>()
            finish()
        }
        setContentView(R.layout.activity_auth)

        btnLogin.setOnClickListener {
            launch {

                authDialog.show()

                val response = Repository.authenticate(
                    login.text.toString(),
                    password.text.toString()
                )
                authDialog.hide()
                if (response.isSuccessful) {
                    toast(getString(R.string.auth_success)).show()
                    setUserAuth(response.body()?.token ?: "")
                    startActivity<FeedActivity>()
                    finish()
                } else {
                    toast(getString(R.string.auth_error))
                }

            }
        }
    }

    override fun onStop() {
        super.onStop()
        cancel()
        authDialog.hide()
    }

    private fun isAuthenticated() =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).getString(
            AUTHENTICATED_SHARED_KEY,
            ""
        )?.isNotEmpty() ?: false

    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit().putString(
            AUTHENTICATED_SHARED_KEY,
            token
        ).commit()
}
