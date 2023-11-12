package com.jp.ruutchallenge.ui.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.ruutchallenge.data.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
): ViewModel() {

    fun getData() {
        viewModelScope.launch {
            apiRepository.getData()
        }
    }

}