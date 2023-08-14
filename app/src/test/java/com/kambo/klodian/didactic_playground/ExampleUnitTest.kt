package com.kambo.klodian.didactic_playground

import android.view.View
import android.widget.EditText
import kotlinx.coroutines.*
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)

        runBlocking {
            supervisorScope {
                // child CR
                launch {
                    throw IllegalStateException("IS")
                }.join()

                // child CR
                val def = async {
                    println(" ----> Async")
                    throw RuntimeException("RE")
                }

                runCatching {
                    def.await()
                }.onFailure(::println)
            }
        }
    }
}

data class User(val name: String)
data class UiUser(val name: String)

inline fun repeatInlineFunction(times: Int, action: () -> Unit) {
    for (index in 0 until times) {
        action()
    }
}

/*
inline fun <T> reifiedErrorFunction() {
    //Error Cannot use 'T' as reified type parameter. Use a class instead.
    // does not compile
    print(T::class.simpleName)
}
*/

inline fun <reified T> reifiedFunction() {
    print(T::class.simpleName)
}

/** Non compiling example
    inline fun EditText.customizedOnFocusChangeListener(
    functionFocus:() -> Unit,
    functionUnfocused: () -> Unit
    ){
    this.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->
    if (focus){
    //Error Can't inline 'functionFocus' here: it may contain non-local returns.
    functionFocus()
    }else{
    //Error Can't inline 'functionUnfocused' here: it may contain non-local returns.
    functionUnfocused()
    }
    }
}
 */


inline fun EditText.customizedOnFocusChangeListener(
    crossinline functionFocus: () -> Unit,
    crossinline functionUnfocused: () -> Unit
) {
    this.onFocusChangeListener = View.OnFocusChangeListener { _, focus ->

        if (focus) {
            //Error Can't inline 'functionFocus' here: it may contain non-local returns.
            functionFocus()
            Unit
        } else {
            //Error Can't inline 'functionUnfocused' here: it may contain non-local returns.
            functionUnfocused()
        }
    }
}