package com.AngelHernandez.mytodoapp.ui.view.home

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.AngelHernandez.mytodoapp.R
import com.AngelHernandez.mytodoapp.databinding.ActivityHomeBinding
import com.AngelHernandez.mytodoapp.ui.view.dependencias.PreferencesManager
import com.AngelHernandez.mytodoapp.ui.view.tareas.TareasViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private  lateinit var navController: NavController
    private val homeViewModel by viewModels<HomeViewModel>() //enganchar viewModel

    @Inject
    lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        initUi()

    }

    private fun initUi() {
        initNavigation()
        getIdGroupDefault()
        initObserves()
    }

    private fun initObserves() {

        homeViewModel.idGroupTaskDefaultResult.observe(this) { result ->
            if (result.isSuccess) {

                val user = result.getOrNull()
                preferencesManager.saveIdGroupTaskDefault(user!!.default_grouptask_id.toString())

            } else {
                val exception = result.exceptionOrNull()
                Log.e("ERROR", "No se optubo el id de grouptasjk default: ${exception?.message}")
                Toast.makeText(this, "no id group task: ${exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getIdGroupDefault() {
        val userId = preferencesManager.getId()
        homeViewModel.getIdGroupTaskDefault(userId!!)

    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigation.setupWithNavController(navController)

    }
}