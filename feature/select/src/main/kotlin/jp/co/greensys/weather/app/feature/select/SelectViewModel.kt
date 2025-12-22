package jp.co.greensys.weather.app.feature.select

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.co.greensys.weather.app.core.common.result.Result
import jp.co.greensys.weather.app.core.domain.usecase.GetForecastDataByNameUseCase
import jp.co.greensys.weather.app.core.model.ForecastData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectViewModel @Inject constructor(
    private val getForecastDataByNameUseCase: GetForecastDataByNameUseCase,
) : ViewModel() {
    private val forecastData = MutableStateFlow<Result<ForecastData>?>(null)

    val selectUiState: StateFlow<SelectUiState> = forecastData.map {
        when (it) {
            null -> SelectUiState.Init
            is Result.Loading -> SelectUiState.Loading
            is Result.Success -> SelectUiState.Success(forecastData = it.data)
            is Result.Error -> SelectUiState.Error(error = it.error)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5_000),
        initialValue = SelectUiState.Init,
    )

    fun getForecastData(cityName: String) {
        viewModelScope.launch {
            forecastData.value = Result.Loading
            forecastData.value = getForecastDataByNameUseCase(cityName = cityName)
        }
    }

    fun clearForecastData() {
        viewModelScope.launch {
            forecastData.value = null
        }
    }
}
