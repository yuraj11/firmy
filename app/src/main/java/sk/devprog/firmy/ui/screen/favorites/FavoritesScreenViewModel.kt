package sk.devprog.firmy.ui.screen.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.devprog.firmy.R
import sk.devprog.firmy.data.repository.FavoritesRepository
import sk.devprog.firmy.ui.common.model.UiError
import sk.devprog.firmy.ui.screen.detail.model.mapper.CompanyListEntityConverter
import sk.devprog.firmy.ui.screen.search.model.CompanyListUiModel
import sk.devprog.firmy.util.TextResource
import javax.inject.Inject

@HiltViewModel
class FavoritesScreenViewModel @Inject constructor(
    private val favoritesRepository: FavoritesRepository,
    private val companyListEntityConverter: CompanyListEntityConverter,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            favoritesRepository.getAllFavorites().collect { items ->
                val mappedItems = items.map { companyListEntityConverter.mapEntityToUiModel(it) }
                    .sortedByDescending { it.createdAt }

                if (items.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            items = mappedItems,
                            error = null
                        )
                    }
                } else {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            items = emptyList(),
                            error = UiError(
                                TextResource.Id(R.string.favorites_empty),
                                R.drawable.ic_outline_heart_broken
                            )
                        )
                    }

                }
            }
        }
    }

    data class UiState(
        val items: List<CompanyListUiModel> = emptyList(),
        val isLoading: Boolean = false,
        val error: UiError? = null,
    )
}
