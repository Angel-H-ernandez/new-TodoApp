package com.AngelHernandez.mytodoapp.ui.view.tareas



import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.AngelHernandez.mytodoapp.R
import com.AngelHernandez.mytodoapp.databinding.FragmentTareasBinding
import com.AngelHernandez.mytodoapp.ui.view.tareas.adapter.TaskAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TareasFragment : Fragment() {

    private var _binding: FragmentTareasBinding? = null ;
    private val binding get() = _binding!!

    private lateinit var taskadapter: TaskAdapter //creamos el adapter

    private val tareasViewModel by viewModels<TareasViewModel>() //enganchar viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        _binding = FragmentTareasBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        initListeners()
    }

    private fun initListeners() {
        binding.BtAddTask.setOnClickListener {
            showDialog()//
        }
    }


    private fun showDialog() {
        //Crear un dialogo para agregar una tarea
        val dialogo = Dialog(requireContext())
        dialogo.setContentView(R.layout.dialog_task)

        //poner bordes redondeados a a la ventana dialog
        dialogo.window?.setBackgroundDrawableResource(R.drawable.border_redonded_dialog)

        var nameTask : EditText =  dialogo.findViewById(R.id.EtTask)
        var botonCancelar : Button = dialogo.findViewById(R.id.BtCancelar)
        var botonAceptar : Button = dialogo.findViewById(R.id.btnNewTask) //

        botonAceptar.setOnClickListener {
            Log.d("INFO contenido de la newTask", nameTask.text.toString())
            tareasViewModel.addTask(nameTask.text.toString())
            dialogo.hide()

        }
        botonCancelar.setOnClickListener {
            dialogo.hide()
        }

        dialogo.show()
    }

    private fun initUi() {
        initList()
        initUiState()
        initProgressBar()

    }

    private fun initProgressBar() {
        lifecycleScope.launch{
            repeatOnLifecycle(Lifecycle.State.STARTED){ //cuando incicie el ciclo de vida
                tareasViewModel.isLoading.collect{
                    Log.i("INFO", "Loading")
                    binding.ProgressBar.visibility = View.GONE
                }

            }
        }
    }

    private fun initList() {
        taskadapter = TaskAdapter(completedTaskFunction = { task ->

            tareasViewModel.completedTask(task)


        }) //incializar recicler view
        binding.rvTask.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = taskadapter
        }

    }

    private fun initUiState() {
        //ESTA CORRUTINA SE ENGANCHA AL CICLO DE VIDA DEL ACTIVITI O FRAGMENT, SIEMPRE ES MEJOR USAR ESTAS
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){ //cuando incicie el ciclo de vida
                tareasViewModel.taskResult.collect{ //enganchate al variable estate flot que creamos
                    //cambios en la lista
                    Log.e("INFO fragment", "taReas: $it") //mostrar los datos en consola
                    //actualizar la lista en el adapter de la recicler view
                    taskadapter.updateTasks(it)
                }

            }
        }
    }

    /*private fun initTask() {
        Log.e("INFO", "TareasFragment")
        tareasViewModel.getTask(3, false)
    }*/


}