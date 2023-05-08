package sk.devprog.firmy.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanySearchParameters
import sk.devprog.firmy.data.repository.CodeListRepository
import sk.devprog.firmy.data.repository.CompanyRepository
import sk.devprog.firmy.ui.common.model.UiError
import sk.devprog.firmy.ui.screen.search.model.CompanyListUiModel
import sk.devprog.firmy.ui.screen.search.model.mapper.toListUiModel
import sk.devprog.firmy.util.TextResource
import javax.inject.Inject

@HiltViewModel
class SearchScreenViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val codeListRepository: CodeListRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun handleAction(action: UiAction) {
        viewModelScope.launch {
            when (action) {
                is UiAction.StartSearch -> {
                    _uiState.update { it.copy(isFullSearch = false) }
                    searchCompany(action.searchParameters)
                }

                is UiAction.ToggleFullSearch -> {
                    _uiState.update { it.copy(isFullSearch = !_uiState.value.isFullSearch) }
                }

                is UiAction.RequestCodeList -> {
                    _uiState.update { it.copy(codeList = emptyList()) }

                    val items =
                        codeListRepository.getCodeListByName(action.codeListId).map { it.value }
                    _uiState.update { it.copy(codeList = items) }
                }
            }
        }
    }

    private suspend fun searchCompany(searchParameters: CompanySearchParameters) {
        _uiState.update { it.copy(isLoading = true, items = emptyList()) }

        companyRepository.searchCompany(searchParameters)
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        items = result.results.map { result -> result.toListUiModel() },
                        error = if (result.results.isEmpty()) UiError(
                            TextResource.Id(R.string.search_no_results),
                            R.drawable.ic_outline_search_off
                        ) else null,
                        isLoading = false
                    )
                }
            }.onFailure {
                _uiState.update {
                    it.copy(
                        error = UiError(
                            TextResource.Id(R.string.search_connection_error),
                            R.drawable.ic_outline_wifi_off
                        ),
                        isLoading = false
                    )
                }
            }
    }

    sealed class UiAction {
        data class StartSearch(val searchParameters: CompanySearchParameters) : UiAction()
        data class RequestCodeList(val codeListId: String) : UiAction()
        object ToggleFullSearch : UiAction()
    }

    data class UiState(
        val items: List<CompanyListUiModel> = emptyList(),
        val codeList: List<String> = emptyList(),
        val isLoading: Boolean = false,
        val error: UiError? = null,
        val isFullSearch: Boolean = true
    )
}
