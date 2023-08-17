package com.kambo.klodian.didactic_playground.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.DialogFragmentBinding

class MessageFragmentDialog: DialogFragment(R.layout.dialog_fragment){

    private lateinit var binding : DialogFragmentBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogFragmentBinding.bind(view)

        isCancelable = false

        binding.icon.setOnClickListener {
            dismiss()
        }
    }
}