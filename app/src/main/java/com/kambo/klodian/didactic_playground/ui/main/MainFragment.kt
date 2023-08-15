package com.kambo.klodian.didactic_playground.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.FragmentMainBinding

/**
 *
 * A [fragment](https://developer.android.com/guide/fragments)
 * is a piece of the User Interface contained in an Activity.
 * It' can't exist outside of an activity, and it depends on that.
 * Has his own life cycle [Fragment lifecycle](https://developer.android.com/guide/fragments/lifecycle)
 */
class MainFragment : Fragment(R.layout.fragment_main) {

    companion object {
        fun newInstance() = MainFragment()
    }

    // provides the ui data
    private lateinit var viewModel: MainViewModel
    private lateinit var binding : FragmentMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
    }

}

class BFragment : Fragment(R.layout.fragment_b)