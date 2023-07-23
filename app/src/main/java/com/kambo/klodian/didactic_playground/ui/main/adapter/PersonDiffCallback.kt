package com.kambo.klodian.didactic_playground.ui.main.adapter

import androidx.recyclerview.widget.DiffUtil
import com.kambo.klodian.didactic_playground.ui.main.entities.UiPerson

internal class PersonDiffCallback : DiffUtil.ItemCallback<UiPerson>() {
    override fun areItemsTheSame(oldItem: UiPerson, newItem: UiPerson): Boolean {
        // data class advantage
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: UiPerson, newItem: UiPerson): Boolean {
        // data class advantage
        return oldItem == newItem
    }
}