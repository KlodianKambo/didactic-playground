package com.kambo.klodian.didactic_playground.ui.main

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.get
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.FragmentMainBinding

/**
 *
 * A [fragment](https://developer.android.com/guide/fragments)
 * is a piece of the User Interface contained in an Activity.
 * It' can't exist outside of an activity, and it depends on that.
 * Has his own life cycle [Fragment lifecycle](https://developer.android.com/guide/fragments/lifecycle)
 */
class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding

    private val settingsPreferences: SharedPreferences by lazy {
        requireContext()
            .applicationContext
            .getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    private val userPreferences: SharedPreferences by lazy {
        requireContext()
            .applicationContext
            .getSharedPreferences("user", Context.MODE_PRIVATE)
    }

    companion object {
        fun newInstance() = MainFragment()
        private const val KEY_NAME = "KEY_NAME"
        private const val KEY_TERMS = "KEY_TERMS"
        private const val KEY_RADIO = "KEY_RADIO"
        private const val KEY_SPINNER = "KEY_SPINNER"
    }

    // provides the ui data
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(view)

        binding.editText.setText(settingsPreferences.getString(KEY_NAME, null))
        binding.editText.doAfterTextChanged {
            settingsPreferences
                .edit()
                .putString(KEY_NAME, it.toString())
                .apply()
        }

        // hook click listener to button
        // setOnClickListener is from View, button inherits from View. See inheritance of the OOP
        binding.button.setOnClickListener {
            val name = binding.editText.text?.toString()
            Toast.makeText(requireContext(), "Hello, $name!", Toast.LENGTH_SHORT).show()
        }

        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            group
                .indexOfChild(view.findViewById(checkedId))
                .takeIf { it > -1 }
                ?.also { childIndex ->
                    settingsPreferences
                        .edit()
                        .putInt(KEY_RADIO, childIndex)
                        .apply()
                }
        }

        val selectedRadioIndex = settingsPreferences.getInt(KEY_RADIO,0)
        binding.radioGroup.check(binding.radioGroup[selectedRadioIndex].id)

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            settingsPreferences.edit().putBoolean(KEY_TERMS, isChecked).apply()
        }

        binding.checkBox.isChecked = settingsPreferences.getBoolean(KEY_TERMS, false)

        addSpinner()
        return view
    }

    private fun addSpinner() {
        // Spinner options
        val spinnerData = listOf("Option 1", "Option 2", "Option 3")

        val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                spinnerData
            )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        binding.spinner.adapter = spinnerAdapter

        // Spinner selection
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                settingsPreferences.edit()
                    .putInt(KEY_SPINNER, position)
                    .apply()

                // consumer action on selection
                val selectedOption = spinnerData[position]
                Toast.makeText(
                    requireContext(),
                    "Selected option: $selectedOption",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        val spinnerIndex = settingsPreferences.getInt(KEY_SPINNER, 0)
        binding.spinner.setSelection(spinnerIndex)
    }
}