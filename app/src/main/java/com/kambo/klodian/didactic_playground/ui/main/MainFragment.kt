package com.kambo.klodian.didactic_playground.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Can be made more readable with private functions that will add just one component
        // then add behaviours like click listeners etc...
        val rootView = inflater.inflate(R.layout.fragment_main, container, false)
        val relativeLayout = rootView.findViewById<RelativeLayout>(R.id.relativeLayout)

        val textView = TextView(requireContext())
        textView.id = View.generateViewId()
        textView.text = "Hello World!"
        textView.textSize = 24f
        val textLayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        textLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        textLayoutParams.topMargin = 16.dpToPx()
        relativeLayout.addView(textView, textLayoutParams)

        val button = Button(requireContext())
        button.text = "Click Me"
        button.id = View.generateViewId()
        val buttonLayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        buttonLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL)
        buttonLayoutParams.addRule(RelativeLayout.BELOW, textView.id)
        buttonLayoutParams.topMargin = 16.dpToPx()
        relativeLayout.addView(button, buttonLayoutParams)

        val editText = EditText(requireContext())
        editText.hint = "Enter your name"
        editText.id = View.generateViewId()
        val editLayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        editLayoutParams.addRule(RelativeLayout.BELOW, button.id)
        editLayoutParams.topMargin = 16.dpToPx()
        editLayoutParams.marginStart = 16.dpToPx()
        editLayoutParams.marginEnd = 16.dpToPx()
        relativeLayout.addView(editText, editLayoutParams)

        // confusion since you need first to add the edit text then get set the button click listener
        button.setOnClickListener {
            val name = editText.text?.toString()
            Toast.makeText(requireContext(), "Hello, $name!", Toast.LENGTH_SHORT).show()
        }

        val checkBox = CheckBox(requireContext())
        checkBox.id = View.generateViewId()
        checkBox.text = "Agree to Terms"
        val checkLayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        checkLayoutParams.addRule(RelativeLayout.BELOW, editText.id)
        checkLayoutParams.topMargin = 16.dpToPx()
        checkLayoutParams.marginStart = 16.dpToPx()
        checkLayoutParams.marginEnd = 16.dpToPx()
        relativeLayout.addView(checkBox, checkLayoutParams)

        val radioGroup = RadioGroup(requireContext())
        radioGroup.id = View.generateViewId()
        val radioGroupLayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        radioGroupLayoutParams.addRule(RelativeLayout.BELOW, checkBox.id)
        radioGroupLayoutParams.topMargin = 16.dpToPx()
        radioGroupLayoutParams.marginStart = 16.dpToPx()
        radioGroupLayoutParams.marginEnd = 16.dpToPx()
        relativeLayout.addView(radioGroup, radioGroupLayoutParams)

        val radio1 = RadioButton(requireContext())
        radio1.text = "Option 1"
        val radio1LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        radio1LayoutParams.topMargin = 16.dpToPx()
        radioGroup.addView(radio1, radio1LayoutParams)

        val radio2 = RadioButton(requireContext())
        radio2.text = "Option 2"
        val radio2LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        radio2LayoutParams.addRule(RelativeLayout.BELOW, radio1.id)
        radio2LayoutParams.topMargin = 16.dpToPx()
        radioGroup.addView(radio2, radio2LayoutParams)

        val radio3 = RadioButton(requireContext())
        radio3.text = "Option 3"
        val radio3LayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        radio3LayoutParams.addRule(RelativeLayout.BELOW, radio2.id)
        radio3LayoutParams.topMargin = 16.dpToPx()
        radioGroup.addView(radio3, radio3LayoutParams)

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton: RadioButton = relativeLayout.findViewById(checkedId)
            val selectedOption = radioButton.text.toString()
            Toast.makeText(
                requireContext(),
                "Selected option: $selectedOption",
                Toast.LENGTH_SHORT
            ).show()
        }

        val spinner = Spinner(requireContext())
        val spinnerLayoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        spinnerLayoutParams.addRule(RelativeLayout.BELOW, radioGroup.id)
        spinnerLayoutParams.topMargin = 16.dpToPx()
        spinnerLayoutParams.marginStart = 16.dpToPx()
        spinnerLayoutParams.marginEnd = 16.dpToPx()
        relativeLayout.addView(spinner, spinnerLayoutParams)

        val spinnerData = listOf("Option 1", "Option 2", "Option 3")
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            spinnerData
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = spinnerAdapter

        // Spinner selection
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
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

        return rootView
    }

    private fun Int.dpToPx(): Int {
        val density = resources.displayMetrics.density
        return (this * density + 0.5f).toInt()
    }
}