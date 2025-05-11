package vn.trialapp.mediaplayerdev.features.songs

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import vn.trialapp.mediaplayerdev.usecases.GetSongsFirebaseUseCase
import vn.trialapp.mediaplayerdev.utils.LogUtil
import vn.trialapp.mediaplayerdev.utils.ResultStatus
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongsViewModel @Inject constructor(
    private val getSongsFirebaseUseCase: GetSongsFirebaseUseCase
): ViewModel() {

    private val _uiState = MutableStateFlow<SongsUiState>(SongsUiState.Loading)
    val uiState: StateFlow<SongsUiState> = _uiState

    init {
        requestGetSongsFirebase()
    }

    private fun requestGetSongsFirebase() {
        viewModelScope.launch {
            LogUtil.traceIn()
            _uiState.value = SongsUiState.Loading

            when (val result = getSongsFirebaseUseCase()) {
                is ResultStatus.Success -> {
                    _uiState.value = SongsUiState.Success(result.data ?: emptyList())
                }
                is ResultStatus.Error -> {
                    _uiState.value = SongsUiState.Error(result.message ?: "Unknown error")
                }
            }
            LogUtil.traceOut()
        }
    }
}