package sk.devprog.firmy.ui.screen.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import sk.devprog.firmy.ui.common.widget.ErrorLayout
import sk.devprog.firmy.ui.screen.search.widget.CompanyListItem
import sk.devprog.firmy.ui.theme.AppTheme

@Composable
fun FavoritesScreen(
    onItemClick: (id: Int, title: String) -> Unit,
    viewModel: FavoritesScreenViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    FavoritesScreenContent(
        uiState = state,
        onItemClick = onItemClick,
    )
}

@Composable
private fun FavoritesScreenContent(
    uiState: FavoritesScreenViewModel.UiState,
    onItemClick: (id: Int, title: String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

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

@Preview
@Composable
private fun FavoritesScreenPreview(
) {
    AppTheme {
        FavoritesScreenContent(
            uiState = FavoritesScreenViewModel.UiState(emptyList()),
            onItemClick = { _, _ -> },
        )
    }
}
