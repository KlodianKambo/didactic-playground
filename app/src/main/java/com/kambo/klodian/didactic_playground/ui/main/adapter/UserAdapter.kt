package com.kambo.klodian.didactic_playground.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.ItemUserBinding


data class UiUser(val name: String, val surname: String, val selected: Boolean)

private fun diffUtils() = object : DiffUtil.ItemCallback<UiUser>() {
    override fun areItemsTheSame(oldItem: UiUser, newItem: UiUser) = oldItem == newItem
    override fun areContentsTheSame(oldItem: UiUser, newItem: UiUser) = oldItem == newItem
}


class UserAdapter(private val action: (UiUser) -> Unit) :
    ListAdapter<UiUser, UserViewHolder>(diffUtils()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return ItemUserBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
            .run { UserViewHolder(this, action) }
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}


class UserViewHolder(private val binding: ItemUserBinding, private val action: (UiUser) -> Unit) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(user: UiUser) {

        binding.name.text = user.name

        val textColor = if(user.selected){
            R.color.teal_700
        } else {
            R.color.black
        }
        binding.name.setTextColor(ContextCompat.getColor(binding.root.context,textColor))
        binding.root.setOnClickListener {
            action(user)
        }
    }
}