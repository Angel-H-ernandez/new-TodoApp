package com.AngelHernandez.mytodoapp.ui.view.login

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.AngelHernandez.mytodoapp.R
import com.AngelHernandez.mytodoapp.data.model.UserProvider
import com.AngelHernandez.mytodoapp.databinding.ActivitySignUpBinding
import com.AngelHernandez.mytodoapp.domain.SignUpUseCase
import com.AngelHernandez.mytodoapp.ui.view.dependencias.PreferencesManager
import com.AngelHernandez.mytodoapp.ui.view.home.HomeActivity

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private val singUpViewModel by viewModels<SignUpViewModel>() //asi se engancha el view model al ctivity
    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
        initObservers() // Inicializar los observadores para LiveData

    }

    private fun initListeners() {
        binding.BtSignUp.setOnClickListener {
            singUp()
        }
    }

    private fun singUp() {
        val name = binding.EtNameUser.text.toString()
        val email = binding.EtEmail.text.toString()
        val password = binding.EtPassword.text.toString()
        val passwordConfirm = binding.EtPasswordConfirm.text.toString()
        singUpViewModel.signUp(name, email, password, passwordConfirm) //MANDAR LOS DATOS DE REGISTRO AL VIEWMODE
    }

    private fun initObservers() {
        // Observa el resultado del inicio de sesión
        singUpViewModel.signUpResult.observe(this) { result ->
            if (result.isSuccess) {
                // Inicio de sesión exitoso
                val user = result.getOrNull()
                Log.i("INFO", "Sigup successful: $user")
                preferencesManager.saveId(user!!.id.toString())
                val intent = Intent(this, HomeActivity::class.java)

                startActivity(intent)
                finish();

            } else {
                // Error en el inicio de sesión
                val exception = result.exceptionOrNull()
                Log.e("ERROR", "Login failed: ${exception?.message}")
                Toast.makeText(this, "Login failed: ${exception?.message}", Toast.LENGTH_LONG)
                    .show()
                marcarErrorCampos()
            }
        }

        singUpViewModel.dialogEvent.observe(this) { event ->
            event?.let { (title, message) ->
                AlertDialog.Builder(this)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton("Aceptar") { dialog, _ -> dialog.dismiss() }
                    .show()
                singUpViewModel.dialogShown()


            }
           //
        }

    }

    private fun marcarErrorCampos() {
        binding.EtEmail.setBackgroundResource(R.drawable.edit_text_error)
        binding.EtPassword.setBackgroundResource(R.drawable.edit_text_error)
        binding.EtNameUser.setBackgroundResource(R.drawable.edit_text_error)
        binding.EtPasswordConfirm.setBackgroundResource(R.drawable.edit_text_error)
    }


}
