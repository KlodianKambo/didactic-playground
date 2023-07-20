package com.kambo.klodian.didactic_playground

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kambo.klodian.didactic_playground.databinding.SecondActivityBinding

class SecondActivity  : AppCompatActivity() {

    private lateinit var binding: SecondActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
}