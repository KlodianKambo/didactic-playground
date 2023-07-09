package com.kambo.klodian.didactic_playground.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.ChildFormLayoutBinding
import com.kambo.klodian.didactic_playground.databinding.FragmentMainBinding

/**
 *
 * A [fragment](https://developer.android.com/guide/fragments)
 * is a piece of the User Interface contained in an Activity.
 * It' can't exist outside of an activity, and it depends on that.
 * Has his own life cycle [Fragment lifecycle](https://developer.android.com/guide/fragments/lifecycle)
 */
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    // provides the ui data
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(rootView)

        binding.btnGenerate.setOnClickListener {
            val numChildren = binding.etNumChildren.text.toString().toIntOrNull() ?: 0
            setChildForm(numChildren)
            hideKeyboard()
        }

        return rootView
    }

    private fun setChildForm(numChildren: Int) {

        binding.childFormsContainer.removeAllViews()

        for (i in 1..numChildren) {
            val childFormView = layoutInflater.inflate(R.layout.child_form_layout, binding.childFormsContainer, false)
            val childFormLayoutBinding = ChildFormLayoutBinding.bind(childFormView)
            childFormLayoutBinding.tvChildNumber.text = getString(R.string.child_number, i)

            binding.childFormsContainer.addView(childFormView)
        }
    }

    private fun hideKeyboard() {
        val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }
}