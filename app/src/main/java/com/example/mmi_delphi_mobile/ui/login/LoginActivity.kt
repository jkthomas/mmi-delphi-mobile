package com.example.mmi_delphi_mobile.ui.login

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast

import com.example.mmi_delphi_mobile.R
import com.example.mmi_delphi_mobile.ui.feed.FeedActivity
import java.net.ConnectException

class LoginActivity : AppCompatActivity() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        checkPermissions()
        configureNetworkThreadPolicy()

        val username = findViewById<EditText>(R.id.username)
        val password = findViewById<EditText>(R.id.password)
        val login = findViewById<Button>(R.id.login)
        val register = findViewById<Button>(R.id.register)
        val loading = findViewById<ProgressBar>(R.id.loading)

        loginViewModel = ViewModelProviders.of(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        password.apply {
            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            username.text.toString(),
                            password.text.toString()
                        )
                }
                false
            }

            login.setOnClickListener {
                loading.visibility = View.VISIBLE
                try{
                    val wasLoginSuccessful = loginViewModel.login(username.text.toString(), password.text.toString())
                    if(wasLoginSuccessful){
                        Toast.makeText(this@LoginActivity, "Logged in successfully!!", Toast.LENGTH_LONG).show()

                        val intent = Intent(this@LoginActivity, FeedActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@LoginActivity, "Login was NOT successful. Please try again with proper credentials!", Toast.LENGTH_LONG).show()
                    }
                    loading.visibility = View.INVISIBLE
                } catch (e: ConnectException) {
                    Toast.makeText(this@LoginActivity, "Unable to communicate with server", Toast.LENGTH_LONG).show()
                    loading.visibility = View.INVISIBLE
                }
            }

            register.setOnClickListener {
                loading.visibility = View.VISIBLE
                try{
                    val wasRegisterSuccessful = loginViewModel.register(username.text.toString(), password.text.toString())
                    if(wasRegisterSuccessful){
                        Toast.makeText(this@LoginActivity, "Registered successfully. Now you can log in!", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(this@LoginActivity, "Register was NOT successful. Please try again with different credentials!", Toast.LENGTH_LONG).show()
                    }
                    loading.visibility = View.INVISIBLE
                } catch (e: ConnectException) {
                    Toast.makeText(this@LoginActivity, "Unable to communicate with server", Toast.LENGTH_LONG).show()
                    loading.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            //permission is not granted - request permisions
            setInternetPermissions()
        }

        //permission granted already - check connection
        if (isOnline()) {
            return
        } else {
            shutdownApplication("No connection with server", "Connection with serwer couldn't be established. Application will shut down.")
        }
    }

    private fun setInternetPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 1)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            shutdownApplication("Permissions not provided", "Permission not granted. Shutting down...")
        }
    }

    private fun shutdownApplication(shutdownAlertTitle: String, shutdownAlertMessage: String) {
        val shutdownAlertBuilder = AlertDialog.Builder(this)
        shutdownAlertBuilder.setTitle(shutdownAlertTitle)
        shutdownAlertBuilder.setMessage(shutdownAlertMessage)
        shutdownAlertBuilder.setPositiveButton("Shutdown") { _, _ ->
            finish()
        }
        val shutdownDialog: AlertDialog = shutdownAlertBuilder.create()
        shutdownDialog.show()
    }

    private fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }

    private fun configureNetworkThreadPolicy() {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
    }
}
