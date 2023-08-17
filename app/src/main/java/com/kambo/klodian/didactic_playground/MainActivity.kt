package com.kambo.klodian.didactic_playground

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.kambo.klodian.didactic_playground.databinding.ActivityMainBinding
import com.kambo.klodian.didactic_playground.ui.main.FragmentProfile
import com.kambo.klodian.didactic_playground.ui.main.FragmentSearch


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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.action_settings -> {
                // Handle Option 2 click
                Snackbar.make(
                    binding.bottomNavigationView,
                    "Pressed ${item.title}",
                    Snackbar.LENGTH_INDEFINITE
                ).run {
                    setAction("Ok") {
                        dismiss()
                    }
                    show()
                }
                true
            }

            R.id.action_learn -> {
                // Handle Option 2 click
                Toast.makeText(this, "Pressed ${item.title}", Toast.LENGTH_SHORT).show()
                true
            }


            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)

        // sets the layout to be rendered for this activity
        setContentView(binding.root)

        // set the custom toolbar
        setSupportActionBar(binding.toolbar)

        // works only for no action bar themes
        // <style name="Theme.Didacticplayground"
        //      parent="Theme.MaterialComponents.DayNight.NoActionBar">

        binding.bottomNavigationView.setOnItemSelectedListener { it ->

            when (it.itemId) {
                R.id.action_search -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentSearch.newInstance())
                        .commitNow()

                    AlertDialog.Builder(this)
                        .setMessage("Hello, didactic playground")
                        .setTitle("Snacks and Dialogs")
                        .setCancelable(false)
                        .setIcon(R.drawable.ic_person_pin)
                        .setPositiveButton("Ok") { dialog, _ ->
                            // TODO implement your logic here, then dismiss
                            dialog.dismiss()
                        }
                        .setNegativeButton("Cancel") { dialog, _ ->
                            // TODO implement your logic here, then dismiss
                            dialog.dismiss()
                        }
                        .show()
                }

                R.id.action_person -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FragmentProfile())
                        .commitNow()
                }
            }
            true
        }

        // this statement provides a guarantee to not replace the fragment on rotation
        if (savedInstanceState == null) {
            // fragmentManager already handles the transaction
            // without this code you will add to the fragment manager an additional
            // replace transaction for every rotation you make. You will have n + 1 fragments in the fragment
            // manager where n = number of device rotations
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, FragmentSearch.newInstance())
                .commitNow()
        }
    }
}