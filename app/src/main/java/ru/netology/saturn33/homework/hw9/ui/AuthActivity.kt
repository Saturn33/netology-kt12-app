package ru.netology.saturn33.homework.hw9.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.ktor.util.KtorExperimentalAPI
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import ru.netology.saturn33.homework.hw9.API_SHARED_FILE
import ru.netology.saturn33.homework.hw9.AUTHENTICATED_SHARED_KEY
import ru.netology.saturn33.homework.hw9.R
import ru.netology.saturn33.homework.hw9.Utils
import ru.netology.saturn33.homework.hw9.repositories.Repository

@KtorExperimentalAPI
class AuthActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var authDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onStart() {
        super.onStart()

        if (isAuthenticated()) {
            startActivity<FeedActivity>()
            finish()
        }
        authDialog = indeterminateProgressDialog(
            title = getString(R.string.auth_title),
            message = getString(R.string.auth_checking_data)
        ).apply {
            setCancelable(false)
            hide()
        }

/* //TODO сделать проверку токена запросом me, когда научимся запрашивать с токеном
        launch {
            authDialog?.show()
            if (isAuthenticated()) {
                if (checkMe())
                {
                    startActivity<FeedActivity>()
                    finish()
                }
                else {
                    //removeUserAuth()
                    login.error = "Авторизация не подтвердилась, введите логин и пароль заново"
                }
            }
            authDialog?.hide()
        }
*/

        btnLogin.setOnClickListener {
            var validationResult: Pair<Boolean, String> = Utils.isLoginValid(login.text.toString())
            if (!validationResult.first) {
                login.error = validationResult.second
                return@setOnClickListener
            }
            validationResult = Utils.isPasswordValid(password.text.toString())
            if (!validationResult.first) {
                password.error = validationResult.second
                return@setOnClickListener
            }

            launch {
                authDialog?.show()
                val response = Repository.authenticate(
                    login.text.toString(),
                    password.text.toString()
                )
                authDialog?.hide()
                if (response.isSuccessful) {
                    toast(getString(R.string.auth_success))
                    setUserAuth(response.body()?.token ?: "")
                    startActivity<FeedActivity>()
                    finish()
                } else {
                    try {
                        val json = JSONObject(response.errorBody()?.string() ?: "")
                        login.error = json.get("error").toString()
                    } catch (e: Exception) {
                        longToast(getString(R.string.unknown_auth_error))
                    }
                }
            }
        }

        btnRegister.setOnClickListener {
            startActivity<RegistrationActivity>()
        }
    }

    private suspend fun checkMe(): Boolean {
        val response = Repository.me()
        return response.isSuccessful && response.body()?.id is Long
    }

    override fun onStop() {
        super.onStop()
        cancel()
        authDialog?.hide()
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

    private fun removeUserAuth() = getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit().remove(AUTHENTICATED_SHARED_KEY).commit()
}
