package com.kambo.klodian.didactic_playground

import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test

class ChildCoroutineTests {


    @Test
    fun `child coroutines with inherited launch stops on parent cancellation`(){
        val otherJob = Job()

        // launch a coroutine to process some kind of incoming request
        runBlocking {

            var result1 = false
            var result2 = false
            var result3 = false
            var result4 = false

            val launchingJob = Job()
            val job = launch(launchingJob) {

                // launch with no CoroutineContext will inherit the CoroutineContext from the parent
                launch {
                    delay(100)
                    ensureActive()
                    result1 = true
                }

                launch {
                    delay(100)
                    ensureActive()
                    result2 = true
                }

                // this creates a new context even with the same job, will not fail
                // launch != launch(launchingJob) even if they have the same job instance
                launch(launchingJob) {
                    delay(100)
                    result3 = true
                }

                launch(otherJob) {
                    delay(100)
                    result4 = true
                }
            }


            delay(50)
            // cancels the job and waits for it's completion
            job.cancelAndJoin()
            delay(100)


            Assert.assertEquals(false, result1)
            Assert.assertEquals(false, result2)

            Assert.assertEquals(true, result3)
            Assert.assertEquals(true, result4)
        }
    }
}