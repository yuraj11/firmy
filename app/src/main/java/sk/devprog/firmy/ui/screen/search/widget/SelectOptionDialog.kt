package sk.devprog.firmy.ui.screen.search.widget

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.ui.theme.Typography
import sk.devprog.firmy.util.removeDiacritics

@Composable
fun SelectOptionDialog(
    title: String,
    itemList: List<String>,
    onSelectOption: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var searchText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    AlertDialog(onDismissRequest = onDismiss, confirmButton = {
        TextButton(onClick = onDismiss, content = {
            Text(
                text = stringResource(id = R.string.search_dialog_options_close)
            )
        })
    }, title = { Text(title) }, text = {
        Column {
            SearchTextField(
                title = stringResource(id = R.string.search_dialog_search),
                text = searchText,
                updateText = { searchText = it })
            Spacer(modifier = Modifier.height(8.dp))
            val filteredItems = itemList.filter { item ->
                item.removeDiacritics().contains(searchText.removeDiacritics(), ignoreCase = true)
            }
            LazyColumn(state = listState, content = {
                items(filteredItems, key = { it }, itemContent = { item ->
                    Text(
                        text = item,
                        style = Typography.bodySmall,
                        modifier = Modifier
                            .clickable { onSelectOption(item) }
                            .padding(8.dp)
                            .fillMaxWidth()
                    )
                })
                coroutineScope.launch { listState.scrollToItem(0) }
            })
        }
    })
}

@Preview
@Composable
private fun SelectOptionDialogPreview() {
    AppTheme {
        SelectOptionDialog(
            title = "Select option",
            itemList = listOf("First item", "Second item"),
            onSelectOption = {},
            onDismiss = {}
        )
    }
}
