package com.kambo.klodian.didactic_playground.data

import com.kambo.klodian.didactic_playground.di.IoDispatcher
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject


data class Person(val name: String, val surname: String)
data class Car(val plate: String)
data class House(val address: String)

data class User(val person: Person, val car: Car, val house: House)

interface Repo {
    suspend fun getUser(): Result<User>
    fun getUsersFlow(): Flow<List<User>>
}

class RepoImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : Repo {

    // Job is inherited
    override suspend fun getUser() = runCatching {
        withContext(ioDispatcher) {

            // Job is inherited
            val personRequest = async {
                println("(!) Performing personRequest on ${Thread.currentThread().name}")
                Thread.sleep(1000)
                // mock
                Person("Klodian", "Kambo")
            }

            val carRequest = async {
                println("(!) Performing carRequest on ${Thread.currentThread().name}")
                Thread.sleep(2000)
                // mock
                Car("XY100ZD")
            }

            val houseRequest = async {
                println("(!) Performing houseRequest on ${Thread.currentThread().name}")
                Thread.sleep(1500)

                throw RuntimeException("error")
                // mock
                House("Street Address")
            }

            User(
                person = personRequest.await(),
                car = carRequest.await(),
                house = houseRequest.await()
            )
        }
    }


    // cold flow
    override fun getUsersFlow() = flow {
        Thread.sleep(100)
        // mock
        val person = Person("Klodian", "Kambo")

        Thread.sleep(200)
        // mock
        val car = Car("XY100ZD")

        Thread.sleep(1000)
        // mock
        val house = House("Street Address")

        val user = User(person, car, house)

        emit(
            listOf(
                user,
                user.copy(person = user.person.copy(name = "Rigert")),
                user.copy(person = user.person.copy(name = "Raimond"))
                )
        )
    }
        // intermediate operators
        // main safety dispatcher (viewModelScope is usually the caller)
        .catch { println("\n(!) ${Thread.currentThread().name} flow error $it") }
        .map { it }
        .also { println("\n(!) ${Thread.currentThread().name} map $it") }
        .filter { it == it }
        .also { println("\n(!) ${Thread.currentThread().name} filter $it") }
        .flowOn(ioDispatcher)

}