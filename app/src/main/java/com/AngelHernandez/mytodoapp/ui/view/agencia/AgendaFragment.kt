package com.AngelHernandez.mytodoapp.ui.view.agencia


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AngelHernandez.mytodoapp.databinding.FragmentAgendaBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint //para que se inyectable
class AgendaFragment : Fragment() {

    private lateinit var _binding: FragmentAgendaBinding;
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAgendaBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}