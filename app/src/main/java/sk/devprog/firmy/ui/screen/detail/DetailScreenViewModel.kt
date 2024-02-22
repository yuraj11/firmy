package sk.devprog.firmy.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.company.CompanyDetail
import sk.devprog.firmy.data.model.MapCoordinates
import sk.devprog.firmy.data.model.sortByDate
import sk.devprog.firmy.data.repository.CompanyRepository
import sk.devprog.firmy.data.repository.FavoritesRepository
import sk.devprog.firmy.data.repository.FinancialStatementsRepository
import sk.devprog.firmy.data.repository.GeocoderRepository
import sk.devprog.firmy.ui.common.model.UiError
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailUiModel
import sk.devprog.firmy.ui.screen.detail.model.mapper.CompanyListEntityConverter
import sk.devprog.firmy.ui.screen.detail.model.mapper.buildFinancesExtraSection
import sk.devprog.firmy.ui.screen.detail.model.mapper.toUiModel
import sk.devprog.firmy.ui.screen.search.model.mapper.toListUiModel
import sk.devprog.firmy.util.TextResource
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel @Inject constructor(
    private val companyRepository: CompanyRepository,
    private val financialStatementsRepository: FinancialStatementsRepository,
    private val geocoderRepository: GeocoderRepository,
    private val favoritesRepository: FavoritesRepository,
    private val companyListEntityConverter: CompanyListEntityConverter,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = savedStateHandle["id"]!!
    private val title: String = savedStateHandle["title"]!!

    private val _uiState = MutableStateFlow(UiState(title = title))
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _effect: Channel<Effect> = Channel(Channel.CONFLATED)
    val effect: Flow<Effect> = _effect.receiveAsFlow()

    private var detail: CompanyDetail? = null

    init {
        viewModelScope.launch {
            launch {
                _uiState.update { it.copy(isFavorite = favoritesRepository.checkIfExists(id)) }
            }
            loadDetail()
        }
    }

    fun handleAction(action: UiAction) {
        viewModelScope.launch {
            when (action) {
                is UiAction.AddRemoveFavorite -> {
                    detail?.let { detail ->
                        if (_uiState.value.isFavorite) {
                            favoritesRepository.deleteFavorite(detail.id)
                            _uiState.update { it.copy(isFavorite = false) }
                        } else {
                            favoritesRepository.addFavorite(
                                companyListEntityConverter.mapUiModelToEntity(detail.toListUiModel())
                            )
                            _uiState.update { it.copy(isFavorite = true) }
                        }
                    }
                }

                is UiAction.Share -> {
                    _effect.send(Effect.ShareDetails)
                }
            }
        }
    }

    private suspend fun loadDetail() {
        _uiState.update { it.copy(isLoading = true, model = null) }

        companyRepository.getDetail(id = id, showHistoricalData = true)
            .onSuccess { result ->
                detail = result
                val address = result.addresses.first()
                loadMap("${address.street} ${address.buildingNumber.orEmpty()}, ${address.municipality}, ${address.country}")

                // Loading financial statements will slow down loading detail but prevents
                // flickering of content when additionally loaded
                val financesExtra = loadFinancialStatements(result.identifiers.sortByDate().first().value)
                val model = result.toUiModel()

                _uiState.update {
                    it.copy(
                        model = model.copy(financesTab = financesExtra + model.financesTab),
                        error = null,
                        isLoading = false
                    )
                }

            }
            .onFailure {
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

    private suspend fun loadFinancialStatements(companyIdentifier: String): List<CompanyDetailSectionUiModel> {
        // Ignore failures, log them in repo
        financialStatementsRepository.searchIdentifiers(companyIdentifier)
            .onSuccess {
                it.id.firstOrNull()?.let { id ->
                    financialStatementsRepository.getIdentifierDetail(id)
                        .onSuccess { detail ->
                            return detail.buildFinancesExtraSection()
                        }
                }
            }
        return emptyList()
    }

    private suspend fun loadMap(address: String) {
        val coordinates = geocoderRepository.resolveAddressCoordinates(address)
        if (coordinates != null) {
            _uiState.update {
                it.copy(mapCoordinates = MapCoordinates(coordinates.latitude, coordinates.longitude))
            }
        }
    }

    sealed class UiAction {
        data object AddRemoveFavorite : UiAction()
        data object Share : UiAction()
    }

    sealed class Effect {
        data object ShareDetails : Effect()
    }

    data class UiState(
        val title: String,
        val mapCoordinates: MapCoordinates? = null,
        val model: CompanyDetailUiModel? = null,
        val isLoading: Boolean = false,
        val error: UiError? = null,
        val isFavorite: Boolean = false,
    )
}
