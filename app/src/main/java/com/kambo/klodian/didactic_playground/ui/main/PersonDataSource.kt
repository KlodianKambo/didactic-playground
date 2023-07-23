package com.kambo.klodian.didactic_playground.ui.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.kambo.klodian.didactic_playground.ui.main.entities.UiPerson
import kotlinx.coroutines.delay
import kotlin.random.Random

class PersonDataSource : PagingSource<Int, UiPerson>() {

    private val mockedNames = listOf(
        "John",
        "Jane",
        "Michael",
        "Emily",
        "David",
        "Sophia",
        "Albert",
        "Maximilian",
        "Berthold",
        "Reinar"
    )
    private val mockedSurnames = listOf(
        "Smith",
        "Johnson",
        "Brown",
        "Williams",
        "Jones",
        "Davis",
        "Jordan",
        "De Perro",
        "Sanchez",
        "Ossas"
    )

    // Simulated list of UiPerson objects (replace this with your actual data source)
    private val personList = generatePersonList(100)

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, UiPerson> {
        try {

            // Async emulation
            delay(1000)

            // Calculate the range of data to load based on the passed-in params
            val start = params.key ?: 0
            val end = start + params.loadSize

            // Load data from the list based on the calculated range
            val persons = personList.subList(start, end)

            // Return the data along with the keys for next and previous pages
            return LoadResult.Page(
                data = persons,
                prevKey = if (start == 0) null else start - 1,
                nextKey = if (end >= personList.size) null else start + params.loadSize
            )
        } catch (exception: Exception) {
            // Handle any errors during data loading
            return LoadResult.Error(exception)
        }
    }

    // This method is used to determine the key for invalidation and refreshing the data source
    override fun getRefreshKey(state: PagingState<Int, UiPerson>): Int? {
        // Return null here since we don't support refreshing the data source by key
        return null
    }


    // private fun
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