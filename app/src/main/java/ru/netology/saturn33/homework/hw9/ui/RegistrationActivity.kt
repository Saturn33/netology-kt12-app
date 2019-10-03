package ru.netology.saturn33.homework.hw9.ui

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.*
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.json.JSONObject
import ru.netology.saturn33.homework.hw9.API_SHARED_FILE
import ru.netology.saturn33.homework.hw9.AUTHENTICATED_SHARED_KEY
import ru.netology.saturn33.homework.hw9.R
import ru.netology.saturn33.homework.hw9.Utils
import ru.netology.saturn33.homework.hw9.repositories.Repository

class RegistrationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var regDialog: ProgressDialog? = null
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
    }

    override fun onStart() {
        super.onStart()
        btnRegister.setOnClickListener {
            val pass1 = password.text.toString()
            val pass2 = password_confirmation.text.toString()
            if (pass1 != pass2) {
                password.error = getString(R.string.passwords_do_not_match)
                return@setOnClickListener
            }

            var validationResult: Pair<Boolean, String> = Utils.isLoginValid(login.text.toString())
            if (!validationResult.first) {
                login.error = validationResult.second
                return@setOnClickListener
            }
            validationResult = Utils.isPasswordValid(pass1)
            if (!validationResult.first) {
                password.error = validationResult.second
                return@setOnClickListener
            }

            job = launch {
                regDialog = indeterminateProgressDialog(
                    title = getString(R.string.registration),
                    message = getString(R.string.registration_attempt)
                ).apply {
                    setCancelable(false)
                    show()
                }
                val response = Repository.register(
                    login.text.toString(),
                    password.text.toString()
                )
                regDialog?.hide()
                if (response.isSuccessful) {
                    toast(getString(R.string.registration_success))
                    setUserAuth(response.body()?.token ?: "")
                    finish()
                } else {
                    try {
                        val json = JSONObject(response.errorBody()?.string() ?: "")
                        login.error = json.get("error").toString()
                    } catch (e: Exception) {
                        longToast(getString(R.string.unknown_registration_error))
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        job?.cancel()
        regDialog?.hide()
    }

    private fun setUserAuth(token: String) =
        getSharedPreferences(API_SHARED_FILE, Context.MODE_PRIVATE).edit().putString(
            AUTHENTICATED_SHARED_KEY,
            token
        ).commit()
}
