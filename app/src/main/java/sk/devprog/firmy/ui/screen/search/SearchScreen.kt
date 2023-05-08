package sk.devprog.firmy.ui.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import sk.devprog.firmy.data.model.company.CompanySearchParameters
import sk.devprog.firmy.ui.common.widget.ErrorLayout
import sk.devprog.firmy.ui.navigation.BottomNavigation
import sk.devprog.firmy.ui.navigation.BottomNavigationItem
import sk.devprog.firmy.ui.screen.search.SearchScreenViewModel.UiAction
import sk.devprog.firmy.ui.screen.search.widget.CompanyListItem
import sk.devprog.firmy.ui.screen.search.widget.SearchAdvancedForm
import sk.devprog.firmy.ui.screen.search.widget.SearchBasicRow
import sk.devprog.firmy.ui.theme.AppTheme

@Composable
fun SearchScreen(
    onItemClick: (id: Int, title: String) -> Unit,
    updateBottomNavigationBadge: (BottomNavigationItem) -> Unit,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    LaunchedEffect(state.items.size) {
        updateBottomNavigationBadge(
            BottomNavigationItem(
                BottomNavigation.Search,
                state.items.size
            )
        )
    }

    SearchScreenContent(
        uiState = state,
        startSearch = { searchParameters ->
            viewModel.handleAction(UiAction.StartSearch(searchParameters))
        },
        onFullSearchToggleClick = {
            viewModel.handleAction(UiAction.ToggleFullSearch)
        },
        onItemClick = onItemClick,
        requestCodeList = { codeListName ->
            viewModel.handleAction(UiAction.RequestCodeList(codeListName))
        }
    )
}

@Composable
private fun SearchScreenContent(
    uiState: SearchScreenViewModel.UiState,
    startSearch: (CompanySearchParameters) -> Unit,
    onFullSearchToggleClick: () -> Unit,
    requestCodeList: (String) -> Unit,
    onItemClick: (id: Int, title: String) -> Unit
) {
    var searchParams by rememberSaveable { mutableStateOf(CompanySearchParameters(onlyActive = true)) }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        SearchBasicRow(
            searchText = searchParams.fullName.orEmpty(),
            modifier = Modifier.padding(16.dp),
            isToggledFullSearch = uiState.isFullSearch,
            updateSearchText = {
                searchParams = searchParams.copy(fullName = it)
            },
            startNameSearch = { startSearch(searchParams) },
            onFullSearchToggleClick = onFullSearchToggleClick
        )

        AnimatedVisibility(visible = uiState.isFullSearch) {
            SearchAdvancedForm(
                modifier = Modifier.fillMaxSize(),
                codeList = uiState.codeList,
                searchParams = searchParams,
                updateParams = {
                    searchParams = it.copy(fullName = searchParams.fullName)
                },
                startSearch = { startSearch(searchParams) },
                requestCodeList = { requestCodeList(it) }
            )
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        if (uiState.error != null) {
            ErrorLayout(error = uiState.error)
        }

        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(uiState.items, key = { it.id }) { item ->
                val itemId = item.id
                val itemTitle = item.companyName.asString()
                CompanyListItem(ui = item, onClick = { onItemClick(itemId, itemTitle) })
            }
        }
    }
}

private class SearchUiStatePreviewProvider :
    CollectionPreviewParameterProvider<SearchScreenViewModel.UiState>(
        listOf(
            SearchScreenViewModel.UiState(),
            SearchScreenViewModel.UiState(isFullSearch = false),
        )
    )

@Preview
@Composable
private fun SearchScreenPreview(
    @PreviewParameter(SearchUiStatePreviewProvider::class) uiState: SearchScreenViewModel.UiState
) {
    AppTheme {
        SearchScreenContent(
            uiState = uiState,
            startSearch = {},
            onFullSearchToggleClick = {},
            onItemClick = { _, _ -> },
            requestCodeList = {}
        )
    }
}
