package com.example.loan_app.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.loan_app.databinding.InfoAppFragmentBinding

class InfoAppFragment : Fragment() {
    private lateinit var binding: InfoAppFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = InfoAppFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonUnderstand.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.buttonClose.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

    }

}