package com.kambo.klodian.didactic_playground.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kambo.klodian.didactic_playground.data.Repo
import com.kambo.klodian.didactic_playground.data.User
import com.kambo.klodian.didactic_playground.ui.main.adapter.UiUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

    private val sharedHotFlow: StateFlow<List<UiUser>> = repo.getUsersFlow()
        .map { it.map { it.toUiUser(false) } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    private val stateFlow = MutableStateFlow<List<UiUser>>(emptyList())

    val userFlow: StateFlow<List<UiUser>> = stateFlow

    private fun User.toUiUser(selected: Boolean) =
        UiUser(selected = selected, name = person.name, surname = person.surname)

    init {
        // CancellableCoroutineScope
        // context = SupervisorJob + Dispatcher.Main.Immediate

        with(viewModelScope) {
            launch {
                sharedHotFlow.collect(stateFlow)
            }
        }
        /*
        with(viewModelScope) {

            launch {
                sharedHotFlow.collect(stateFlow)
            }

            launch {
                repo.getUsersFlow().collectLatest {
                    println("(!) cold flow collect 1 in viewmodelscope $it")
                }
            }

            launch {
                delay(100)
                // getUsersFlow is cold
                repo.getUsersFlow().collectLatest {
                    println("(!) cold flow collect 2 in viewmodelscope $it")
                }
            }

            // CancellableCoroutineScope
            // context = SupervisorJob + Dispatcher.Main.Immediate
            launch {
                delay(10_000)
                sharedHotFlow.collectLatest {
                    println("(!) sharedHotFlow 1 in viewmodelscope $it")
                }
            }

            launch {
                delay(13_000)
                // getUsersFlow is cold
                sharedHotFlow.collectLatest {
                    println("(!) sharedHotFlow 2 in viewmodelscope $it")
                }
            }
        }
         */
    }

    fun fetchUser() {
        viewModelScope.launch {
            repo.getUser()
                .fold(
                    onSuccess = { println(it) },
                    onFailure = { println(it) },
                )
        }
    }

    fun userSelected(uiUser: UiUser) {
        viewModelScope.launch {
            stateFlow.emit(stateFlow.value.map { it.copy(selected = it == uiUser) })
        }
    }
}