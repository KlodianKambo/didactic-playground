package com.kambo.klodian.didactic_playground

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.kambo.klodian.didactic_playground.databinding.SecondActivityBinding

class SecondActivity  : AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("!SecondActivity", "onCreate")


        binding = SecondActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.finishWithResult.setOnClickListener {
            val resultData = binding.editText.text.toString()
            val resultIntent = Intent()
            resultIntent.putExtra("RESULT_DATA", resultData)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }

        binding.finishNoResult.setOnClickListener {
            finish()
        }
    }


    override fun onStart() {
        super.onStart()
        Log.d("!SecondActivity", "onStart")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("!SecondActivity", "onRestart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("!SecondActivity", "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("!SecondActivity", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("!SecondActivity", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("!SecondActivity", "onDestroy")
    }

}