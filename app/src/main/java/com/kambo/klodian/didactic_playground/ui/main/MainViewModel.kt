package com.kambo.klodian.didactic_playground.ui.main

import androidx.lifecycle.ViewModel

/**
 * [ViewModel] class is created when the scope is created, and destroyed when the scope is destroyed
 * It's tied to the activity scope or fragment scope depending on coder's choice.
 * It's retained, thus not recreated on rotation.
 *
 * This class is to be used to expose ui-specific data and update data based on user's interaction.
 * For example: select music from playlist.
 *
 * data class UiMusic(private val title: String, private val artist: String, private val duration: Long)
 *
 * viewModel.selectMusic(UiMusic(...))
 */
class MainViewModel : ViewModel() {

}