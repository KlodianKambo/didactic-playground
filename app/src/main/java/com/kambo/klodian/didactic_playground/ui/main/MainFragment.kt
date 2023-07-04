package com.kambo.klodian.didactic_playground.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.kambo.klodian.didactic_playground.R

/**
 *
 * A [fragment](https://developer.android.com/guide/fragments)
 * is a piece of the User Interface contained in an Activity.
 * It' can't exist outside of an activity, and it depends on that.
 * Has his own life cycle [Fragment lifecycle](https://developer.android.com/guide/fragments/lifecycle)
 */
class MainFragment : Fragment() {

    private var textView: TextView? = null
    private var button: Button? = null
    private var editText: EditText? = null
    private var checkBox: CheckBox? = null
    private var radioButton: RadioButton? = null
    private var spinner: Spinner? = null

    companion object {
        fun newInstance() = MainFragment()
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

        // bind view's components with the xml components
        textView = view.findViewById(R.id.textView)
        button = view.findViewById(R.id.button)
        editText = view.findViewById(R.id.editText)
        checkBox = view.findViewById(R.id.checkBox)
        radioButton = view.findViewById(R.id.radioButton)
        spinner = view.findViewById(R.id.spinner)

        // hook click listener to button
        // setOnClickListener is from View, button inherits from View. See inheritance of the OOP
        button?.setOnClickListener {
            val name = editText!!.text.toString()
            Toast.makeText(requireContext(), "Hello, $name!", Toast.LENGTH_SHORT).show()
        }

        // Spinner options
        val spinnerData: MutableList<String> = ArrayList()
        spinnerData.add("Option 1")
        spinnerData.add("Option 2")
        spinnerData.add("Option 3")

        val spinnerAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, spinnerData)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinner!!.adapter = spinnerAdapter

        // Spinner selection
        spinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View,
                position: Int,
                id: Long
            ) {
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

        return view
    }

}