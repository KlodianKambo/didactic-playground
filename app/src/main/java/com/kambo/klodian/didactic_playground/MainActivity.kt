package com.kambo.klodian.didactic_playground

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.result.ActivityResult
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

    private lateinit var launcher: ActivityResultLauncher<Intent>

    private val pickImageActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val selectedImageUri = intent?.data
                binding.selectedImage.setImageURI(selectedImageUri)
            }
        }

    private val saveImageActivityResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                val uri = intent?.data
                uri?.let {
                    // Grant URI permission to other apps to access the saved image
                    grantUriPermission(
                        packageName,
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    )

                    contentResolver.openOutputStream(it)?.use { outputStream ->
                        // Read the image from res/raw and write it to the MediaStore
                        resources.openRawResource(R.raw.android_robot).use { inputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                }
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("!MainActivity", "onCreate")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        launcher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val resultData = result.data?.getStringExtra("RESULT_DATA")
                    // Handle the result data from the SecondActivity here
                    binding.resultTextView.text = resultData
                } else if (result.resultCode == Activity.RESULT_CANCELED) {
                    // Handle the case where the SecondActivity was canceled
                    binding.resultTextView.text = "Cancelled"
                }
            }

        binding.buttonStartActivity.setOnClickListener {
            // Example usage of startActivity
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
        }

        binding.buttonStartActivityForResult.setOnClickListener {
            // Example usage of startActivityForResult using ActivityResultLauncher
            val intent = Intent(this, SecondActivity::class.java)
            launcher.launch(intent)
        }


        binding.selectImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            pickImageActivityResult.launch(intent)
        }

        binding.saveImageButton.setOnClickListener {
            saveImageToMediaStore()
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("!MainActivity", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("!MainActivity", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("!MainActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("!MainActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("!MainActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("!MainActivity", "onDestroy")
    }

    private fun saveImageToMediaStore() {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/png"
            putExtra(Intent.EXTRA_TITLE, "android_robot.png")
        }

        saveImageActivityResult.launch(intent)
    }
}