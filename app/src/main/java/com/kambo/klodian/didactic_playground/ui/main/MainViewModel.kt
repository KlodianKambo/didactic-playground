package com.kambo.klodian.didactic_playground.ui.main

import androidx.lifecycle.ViewModel
import com.kambo.klodian.didactic_playground.ui.main.entities.UiPerson
import kotlin.random.Random

class MainViewModel : ViewModel() {

    private val mockedNames = listOf("John", "Jane", "Michael", "Emily", "David", "Sophia")
    private val mockedSurnames = listOf("Smith", "Johnson", "Brown", "Williams", "Jones", "Davis")

    fun getContacts() = generatePersonList(50)

    // Function to generate a list of UiPerson objects with mocked data
    private fun generatePersonList(size: Int): List<UiPerson> {
        val personList = mutableListOf<UiPerson>()

        for (i in 1..size) {
            val name = mockedNames.random()
            val surname = mockedSurnames.random()
            val age = Random.nextInt(18, 60)
            val address = "$i Main St"
            val person = UiPerson(name, surname, age, address)
            personList.add(person)
        }

        return personList
    }
}