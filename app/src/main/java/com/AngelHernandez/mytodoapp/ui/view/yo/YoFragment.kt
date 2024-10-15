package com.AngelHernandez.mytodoapp.ui.view.yo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.AngelHernandez.mytodoapp.databinding.FragmentYoBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class YoFragment : Fragment() {

    private lateinit var _binding: FragmentYoBinding;
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentYoBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


}