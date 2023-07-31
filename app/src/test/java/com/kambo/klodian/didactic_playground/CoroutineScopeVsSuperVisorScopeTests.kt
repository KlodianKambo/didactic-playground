package com.kambo.klodian.didactic_playground

import kotlinx.coroutines.*
import org.junit.Assert
import org.junit.Test

class CoroutineScopeVsSuperVisorScopeTests {


    @Test
    fun `coroutineScope child failure leads to coroutine failure`() {
        runBlocking {
            var res1 = false
            var res2 = false

            // first difference with supervisorScope is the tryCatch
            try {
                coroutineScope {
                    launch {
                        delay(100)
                        res1 = true
                    }

                    launch {
                        delay(50)
                        throw RuntimeException("")
                        res2 = true
                    }
                }
            } catch (e: Exception){

            }

            delay(200)

            Assert.assertEquals(false, res1)
            Assert.assertEquals(false, res2)
        }

    }

    @Test
    fun `supervisorScope child failure continues the other children computation`() {

        runBlocking {
            var res1 = false
            var res2 = false

            supervisorScope {

                launch {
                    delay(100)
                    res1 = true
                }

                launch {
                    delay(50)
                    throw RuntimeException("")
                    res2 = true
                }
            }

            delay(200)

            Assert.assertEquals(true, res1)
            Assert.assertEquals(false, res2)
        }
    }
}