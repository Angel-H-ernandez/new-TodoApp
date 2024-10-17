package com.AngelHernandez.mytodoapp.ui.view.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.datastore.core.DataStore
import androidx.lifecycle.lifecycleScope
import com.AngelHernandez.mytodoapp.R
import com.AngelHernandez.mytodoapp.databinding.ActivitySignInBinding
import com.AngelHernandez.mytodoapp.ui.view.dependencias.PreferencesManager
import com.AngelHernandez.mytodoapp.ui.view.home.HomeActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


import java.util.prefs.Preferences
import javax.inject.Inject


// SignInActivity.kt
@AndroidEntryPoint
class SignInActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignInBinding //binding
    private val singInViewModel by viewModels<SignInViewModel>() //asi se engancha el view model al ctivity

    @Inject
    lateinit var preferencesManager: PreferencesManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = preferencesManager.getId()
        if(user != null) goToHome() //verificar si ya estsa logueado

        initListeners()
        initObservers() // Inicializar los observadores para LiveData
    }

    private fun initListeners() {
        binding.BtSignIn.setOnClickListener {
            loggin()
        }
        binding.TvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }



    private fun loggin() {
        val email = binding.EtEmail.text.toString()
        val password = binding.EtPassword.text.toString()
        singInViewModel.signIn(email, password) //MANDAR LOS DATOS DE SESION AL VIEWMODE
    }

    private fun initObservers() {
        // Observa el resultado del inicio de sesión
        singInViewModel.signInResult.observe(this) { result ->
            if (result.isSuccess) {
                // Inicio de sesión exitoso
                val user = result.getOrNull()
                Log.i("INFO", "Login successful: $user")
                Log.i("INFO user id", user!!.id.toString())
                Log.i("INFO user idgrouptask", user.default_grouptask_id.toString())
                Log.e("INFO in", user.toString())
                preferencesManager.saveId(user.id.toString())
                preferencesManager.saveIdGroupTaskDefault(user.default_grouptask_id.toString())
                goToHome()

            } else {

                // Error en el inicio de sesión
                val exception = result.exceptionOrNull()
                Log.e("ERROR", "Login failed: ${exception?.message}")
                marcarErrorCampos()
                Toast.makeText(this, "Login failed: ${exception?.message}", Toast.LENGTH_LONG).show()
            }
        }


        singInViewModel.dialogEvent.observe(this) { event ->
            event?.let { (title, message) ->
                AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
                    .show()
                singInViewModel.dialogShown()
            }
        }


    }

    private fun marcarErrorCampos() {
        binding.EtEmail.setBackgroundResource(R.drawable.edit_text_error)
        binding.EtPassword.setBackgroundResource(R.drawable.edit_text_error)
    }

    private fun goToHome() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
        finish();
    }


}














