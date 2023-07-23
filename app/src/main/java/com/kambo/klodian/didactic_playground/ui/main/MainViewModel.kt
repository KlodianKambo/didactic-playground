package com.kambo.klodian.didactic_playground.ui.main

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kambo.klodian.didactic_playground.ui.main.entities.UiPerson
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {
    val personList: Flow<PagingData<UiPerson>> =
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ).run { Pager(config = this, pagingSourceFactory = { PersonDataSource() }).flow }
}