package com.kambo.klodian.didactic_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kambo.klodian.didactic_playground.ui.main.MainFragment


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