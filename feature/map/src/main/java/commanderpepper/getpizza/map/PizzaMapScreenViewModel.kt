package commanderpepper.getpizza.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class PizzaMapScreenViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<PizzaMapScreenUIState> =
        MutableStateFlow(PizzaMapScreenUIState.Loading)
    val uiState: StateFlow<PizzaMapScreenUIState> = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000L),
        initialValue = PizzaMapScreenUIState.Loading
    )
}