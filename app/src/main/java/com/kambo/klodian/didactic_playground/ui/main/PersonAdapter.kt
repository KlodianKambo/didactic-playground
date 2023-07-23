package com.kambo.klodian.didactic_playground.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kambo.klodian.didactic_playground.R
import com.kambo.klodian.didactic_playground.databinding.ItemPersonBinding
import com.kambo.klodian.didactic_playground.ui.main.entities.UiPerson

class PersonAdapter(private val personList: List<UiPerson>, private val action: ((uiPerson: UiPerson) -> Unit)?) : RecyclerView.Adapter<PersonViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPersonBinding.inflate(inflater, parent, false)
        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val currentPerson = personList[position]
        holder.bind(currentPerson, action)
    }

    override fun getItemCount(): Int {
        return personList.size
    }
}


class PersonViewHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(currentPerson: UiPerson, action: ((uiPerson: UiPerson) -> Unit)?) {
        with(binding){
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