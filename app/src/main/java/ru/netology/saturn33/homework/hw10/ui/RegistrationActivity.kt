package ru.netology.saturn33.homework.hw10.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_registration.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import org.jetbrains.anko.indeterminateProgressDialog
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import org.json.JSONObject
import retrofit2.Response
import ru.netology.saturn33.homework.hw10.R
import ru.netology.saturn33.homework.hw10.Utils
import ru.netology.saturn33.homework.hw10.dto.RegistrationResponseDto
import ru.netology.saturn33.homework.hw10.repositories.Repository
import java.io.IOException

class RegistrationActivity : AppCompatActivity(), CoroutineScope by MainScope() {
    var regDialog: ProgressDialog? = null
    var job: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            finish()
            true
        }
        else {
            return super.onOptionsItemSelected(item)
        }
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
                var response: Response<RegistrationResponseDto>? = null
                regDialog = indeterminateProgressDialog(
                    title = getString(R.string.registration),
                    message = getString(R.string.registration_attempt)
                ).apply {
                    setCancelable(false)
                    show()
                }
                try {
                    response = Repository.register(
                        login.text.toString(),
                        password.text.toString()
                    )
                } catch (e: IOException) {
                    longToast(getString(R.string.unknown_registration_error))
                }

            regDialog?.hide()
                if (response?.isSuccessful == true) {
                    toast(getString(R.string.registration_success))
                    Utils.setUserAuth(this@RegistrationActivity, response.body()?.token ?: "")
                    finish()
                } else {
                    try {
                        val json = JSONObject(response?.errorBody()?.string() ?: "")
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
        regDialog?.dismiss()
    }

}
