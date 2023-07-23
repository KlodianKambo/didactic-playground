package com.kambo.klodian.didactic_playground.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kambo.klodian.didactic_playground.databinding.ItemPersonBinding
import com.kambo.klodian.didactic_playground.ui.main.entities.UiPerson

class PersonListAdapter(private val action: ((uiPerson: UiPerson) -> Unit)?) :
    ListAdapter<UiPerson, PersonViewHolder>(
        PersonDiffCallback()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position), action)
    }
}

class PersonViewHolder(private val binding: ItemPersonBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(currentPerson: UiPerson, action: ((uiPerson: UiPerson) -> Unit)?) {
        with(binding) {
            textName.text = currentPerson.name
            textSurname.text = currentPerson.surname
            textAge.text = "Age: ${currentPerson.age}"
            textAddress.text = "Address: ${currentPerson.address}"

            binding.root.setOnClickListener {
                action?.invoke(currentPerson)
            }
        }
    }
}