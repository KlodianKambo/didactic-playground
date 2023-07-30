package com.kambo.klodian.didactic_playground

import kotlinx.coroutines.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class HelloCoroutineWorldTests {
    @Test
    fun `when launch then print hello then world`() {
        println("Test started")
        // The name of [runBlocking] means that the thread that runs it gets blocked for
        // the duration of the call, until all the coroutines inside runBlocking { ... }
        // complete their execution
        runBlocking {

            // is a coroutine builder. It launches a new coroutine concurrently with the rest of
            // the code, which continues to work independently
            launch { // launch a new coroutine and continue

                // delay is a special suspending function. It suspends the coroutine for a specific
                // time. [Suspending a coroutine does not block the underlying thread], but
                // allows other coroutines to run and use the underlying thread for their code.
                delay(100L) // non-blocking delay for 1 second (default time unit is ms)
                println("World!") // print after delay
            }

            println("Hello") // main coroutine continues while a previous one is delayed
        }

        // blocks until ALL the suspend inside are not completed
        println("Test ended")
    }

    @Test
    fun `when launch and join then print world then hello`() {
        runBlocking {

            val worldJob = launch {
                delay(100L)
                println("World!")
            }

            // suspends the execution of the coroutine until the worldJob is complete
            worldJob.join()
            println("Hello")
        }
    }
}