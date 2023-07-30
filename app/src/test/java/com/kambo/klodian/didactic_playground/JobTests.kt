package com.kambo.klodian.didactic_playground

import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test

class JobTests {

    // supervisor null parent
    // child can fail independently without influencing parent or other children
    // parent j -------------------> V
    //          chj1 ---> X
    //          chj2 ---------> V
    @Test
    fun `supervisor null parent child failure`() {
        val j = SupervisorJob()
        var launch1 = false
        var launch2 = false

        var result1 = false
        var result2 = false

        runBlocking {

            launch(j) {
                launch1 = true
                delay(100L)
                // simulate child failure
                throw RuntimeException("")
                result1 = true
            }


            val j2 = launch(j) {
                launch2 = true
                delay(200L)
                result2 = true
            }

            Assert.assertEquals(2, j.children.count())

            j2.join()

            Assert.assertEquals(true, launch1)
            Assert.assertEquals(true, launch2)

            Assert.assertEquals(false, result1)
            Assert.assertEquals(true, result2)
        }
    }


    // supervisor null parent
    // child failure leads to parent failure and remaining children failure
    // parent j -------------> X
    //          chj1 ---> X
    //          chj2 -------------> X
    @Test
    fun `supervisor with parent child failure`() {

        val j = Job()
        var launch1 = false
        var launch2 = false

        var result1 = false
        var result2 = false

        runBlocking {

            launch(j) {
                launch1 = true
                delay(100L)
                // simulate child failure
                throw RuntimeException("")
                result1 = true
            }

            val j2 = launch(j) {
                launch2 = true
                delay(200L)
                result2 = true
            }

            Assert.assertEquals(2, j.children.count())

            j2.join()

            Assert.assertEquals(true, launch1)
            Assert.assertEquals(true, launch2)

            Assert.assertEquals(false, result1)
            Assert.assertEquals(false, result2)
        }
    }


    // parent failure leads to children failure
    // parent j --------> X
    //          chj1 -------> X
    //          chj2 -------> X
    @Test
    fun `job with parent cancellation and yield`() {

        val j = Job()
        var launch1 = false
        var launch2 = false

        var result1 = false
        var result2 = false

        runBlocking {

            launch(j) {
                launch1 = true
                delay(100L)
                j.cancel()
                // yield() // or ensureActive() checks whether the coroutine is active or cancellation has been requested
                ensureActive()
                result1 = true
            }

            val j2 = launch(j) {
                launch2 = true
                delay(300L)
                result2 = true
            }

            Assert.assertEquals(2, j.children.count())

            j2.join()

            Assert.assertEquals(true, launch1)
            Assert.assertEquals(true, launch2)

            Assert.assertEquals(false, result1)
            Assert.assertEquals(false, result2)
        }
    }
}