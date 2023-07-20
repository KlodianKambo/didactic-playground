package com.kambo.klodian.didactic_playground

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.kambo.klodian.didactic_playground.databinding.ActivityMainBinding


/**
 * [Activity](https://developer.android.com/guide/components/activities/activity-lifecycle)
 * Amongst the 4 main components of an android application,
 * activities are the only one capable of hosting the User Interface
 * and it's building blocks, Views  .
 */
class MainActivity : AppCompatActivity() {

    /**
     * [Lifecycle](https://developer.android.com/guide/components/activities/activity-lifecycle#alc)
     */
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.buttonStartActivity.setOnClickListener {
            // Example usage of startActivity
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.buttonStartActivityForResult.setOnClickListener {
            // Example usage of startActivityForResult using ActivityResultLauncher
            val intent = Intent(this, SecondActivity::class.java)
            getResultActivityLauncher().launch(intent)
        }
    }

    private fun getResultActivityLauncher(): ActivityResultLauncher<Intent> {
        return registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val resultData = result.data?.getStringExtra("RESULT_DATA")
                // Handle the result data from the SecondActivity here
                binding.resultTextView.text = resultData
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                // Handle the case where the SecondActivity was canceled
                binding.resultTextView.text = "Cancelled"
            }
        }
    }
}