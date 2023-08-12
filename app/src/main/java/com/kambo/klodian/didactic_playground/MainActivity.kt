package com.kambo.klodian.didactic_playground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kambo.klodian.didactic_playground.ui.main.MainFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // sets the layout to be rendered for this activity
        setContentView(R.layout.activity_main)

        // this statement provides a guarantee to not replace the fragment on rotation
        if (savedInstanceState == null) {
            // fragmentManager already handles the transaction
            // without this code you will add to the fragment manager an additional
            // replace transaction for every rotation you make. You will have n + 1 fragments in the fragment
            // manager where n = number of device rotations
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
}