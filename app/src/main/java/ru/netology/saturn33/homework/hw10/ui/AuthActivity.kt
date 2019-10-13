package ru.netology.saturn33.homework.hw10.ui

import android.app.ProgressDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_auth.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Response
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.Utils
import ru.netology.saturn33.homework.hw10.dto.AuthenticationResponseDto
import ru.netology.saturn33.homework.hw10.repositories.Repository
import java.io.IOException

class AuthActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var authDialog: ProgressDialog? = null
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
    }

    override fun onStart() {
        super.onStart()
        title = getString(R.string.title_enter)

        val token = Utils.getToken(this)
        Repository.createRetrofit(token)
        if (Utils.isAuthenticated(token)) {
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

            job = launch {
                var response: Response<AuthenticationResponseDto>? = null
                authDialog?.show()
                try {
                    response = Repository.authenticate(
                        login.text.toString(),
                        password.text.toString()
                    )
                } catch (e: IOException) {
                    longToast(getString(R.string.unknown_auth_error))
                }
                authDialog?.hide()
                if (response?.isSuccessful == true) {
                    toast(getString(R.string.auth_success))
                    Utils.setUserAuth(this@AuthActivity, response.body()?.token ?: "")
                    Repository.createRetrofit(response.body()?.token)
                    startActivity<FeedActivity>()
                    finish()
                } else {
                    try {
                        val json = JSONObject(response?.errorBody()?.string() ?: "")
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
        job?.cancel()
        authDialog?.dismiss()
    }
}
