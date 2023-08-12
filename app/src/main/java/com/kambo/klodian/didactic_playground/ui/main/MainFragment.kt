package com.kambo.klodian.didactic_playground.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.FragmentMainBinding
import com.kambo.klodian.didactic_playground.ui.main.adapter.UserAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: FragmentMainBinding
    private val userAdapter = UserAdapter {
        viewModel.userSelected(it)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        binding = FragmentMainBinding.bind(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchUser()

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = userAdapter

        lifecycleScope.launch {
            viewModel.userFlow.collect{
                userAdapter.submitList(it)
            }
        }
    }
}