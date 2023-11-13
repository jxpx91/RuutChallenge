package com.jp.ruutchallenge.ui.views.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jp.ruutchallenge.data.ApiRepository
import com.jp.ruutchallenge.data.room.UserRepository
import com.jp.ruutchallenge.extensions.toTwoDecimalDouble
import com.jp.ruutchallenge.model.MetaData
import com.jp.ruutchallenge.model.Serie
import com.jp.ruutchallenge.model.SerieInfo
import com.jp.ruutchallenge.service.AccountService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiRepository: ApiRepository,
    private val accountService: AccountService,
    private val userRepository: UserRepository,
): ViewModel() {

    val metaData = MutableStateFlow(MetaData.EMPTY)
    val serieInfo = MutableStateFlow(listOf<SerieInfo>())
    val errorMessage = MutableStateFlow("")
    val init = MutableStateFlow(true)

    fun getUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val users = userRepository.getAllUsers()
            users.size
        }
    }

    fun isUserLogged(onNoUserFound: () -> Unit): Boolean {
        viewModelScope.launch {
            init.emit(false)
        }
        if (accountService.hasUser().not()) {
            onNoUserFound()
        }
        return accountService.hasUser()
    }

    fun getData() {
        viewModelScope.launch {
            val result = apiRepository.getData()
            if (result.series.isNullOrEmpty().not()) {
                metaData.emit(result.metaData)
                delay(300)
                serieInfo.emit(calculateSeriesInfo(result.series.keys.toList().reversed(), result.series.values.toList().reversed()))
            } else {
                errorMessage.emit("An error occurred, try later.")
            }
        }
    }

    // require serie list from the outdated to the newest
    private fun calculateSeriesInfo(dates: List<String>, serie: List<Serie>): List<SerieInfo> {
        var lastSerie = serie[0]
        val serieInfo = mutableListOf(
            SerieInfo(
                date = dates[0],
                open = Pair(lastSerie.open.toTwoDecimalDouble(), 0),
                high = Pair(lastSerie.high.toTwoDecimalDouble(), 0),
                low = Pair(lastSerie.low.toTwoDecimalDouble(), 0),
                close = Pair(lastSerie.close.toTwoDecimalDouble(), 0),
                volume = Pair(lastSerie.volume.toTwoDecimalDouble(), 0),
            )
        )
        for (i in 1 until serie.size) {
            serieInfo.add(
                SerieInfo(
                    date = dates[i],
                    open = Pair(
                        serie[i].open.toTwoDecimalDouble(),
                        getSerieIndicator(lastSerie.open.toTwoDecimalDouble(), serie[i].open.toTwoDecimalDouble())
                    ),
                    high = Pair(
                        serie[i].high.toTwoDecimalDouble(),
                        getSerieIndicator(lastSerie.high.toTwoDecimalDouble(), serie[i].high.toTwoDecimalDouble())
                    ),
                    low = Pair(
                        serie[i].low.toTwoDecimalDouble(),
                        getSerieIndicator(lastSerie.low.toTwoDecimalDouble(), serie[i].low.toTwoDecimalDouble())
                    ),
                    close =  Pair(
                        serie[i].close.toTwoDecimalDouble(),
                        getSerieIndicator(lastSerie.close.toTwoDecimalDouble(), serie[i].close.toTwoDecimalDouble())
                    ),
                    volume =  Pair(
                        serie[i].volume.toTwoDecimalDouble(),
                        getSerieIndicator(lastSerie.volume.toTwoDecimalDouble(), serie[i].volume.toTwoDecimalDouble())
                    ),
                )
            )
            lastSerie = serie[i]
        }
        return serieInfo.toList().reversed()
    }

    private fun getSerieIndicator(lastValue: Double, currentValue: Double): Byte {
        return if (lastValue > currentValue) {
            -1 // negative
        } else if (lastValue < currentValue) {
            1 // positive
        } else {
            0 // neutral
        }
    }

}