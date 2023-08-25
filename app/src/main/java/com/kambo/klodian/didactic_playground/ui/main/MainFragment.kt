package com.kambo.klodian.didactic_playground.ui.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
        private const val REQUEST_LOCATION_PERMISSION = 123
    }

    private val locationRepo by lazy {
        LocationRepositoryImpl(
            requireContext().applicationContext,
            Dispatchers.IO,
            Looper.getMainLooper()
        )
    }

    private lateinit var binding: FragmentMainBinding

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
        binding = FragmentMainBinding.bind(
            inflater.inflate(R.layout.fragment_main, container, false)
        )
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Check location permission and request if needed
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Request permission
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                locationRepo.getLocationFlow().collect {
                    it.fold(
                        // TODO use localized string builder
                        onSuccess = { binding.message.text = "${it.latitude}  |  ${it.longitude}" },
                        onFailure = { binding.message.text = "Error: ${it.message}" }
                    )
                }
            }
        }
    }
}