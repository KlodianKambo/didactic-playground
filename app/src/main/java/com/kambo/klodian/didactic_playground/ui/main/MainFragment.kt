package com.kambo.klodian.didactic_playground.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kambo.klodian.didactic_playground.R

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

}