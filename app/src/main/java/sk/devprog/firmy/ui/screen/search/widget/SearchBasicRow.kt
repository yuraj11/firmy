package sk.devprog.firmy.ui.screen.search.widget

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.List
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.theme.AppTheme

@Composable
fun SearchBasicRow(
    searchText: String,
    isToggledFullSearch: Boolean,
    updateSearchText: (String) -> Unit,
    startNameSearch: () -> Unit,
    onFullSearchToggleClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = searchText,
            shape = CircleShape,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            placeholder = { Text(text = stringResource(id = R.string.search_placeholder)) },
            modifier = Modifier.weight(1f),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null
                )
            },
            trailingIcon = {
                Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                    FilledIconToggleButton(
                        checked = isToggledFullSearch,
                        onCheckedChange = { onFullSearchToggleClick() }) {
                        Icon(
                            imageVector = Icons.Outlined.List,
                            contentDescription = stringResource(id = R.string.search_advanced)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(onSearch = {
                startNameSearch()
                focusManager.clearFocus()
            }),
            onValueChange = { updateSearchText(it) },
            singleLine = true
        )
    }
}

@Preview
@Composable
private fun SearchBasicRowPreview() {
    AppTheme {
        SearchBasicRow(
            searchText = "",
            startNameSearch = {},
            onFullSearchToggleClick = {},
            updateSearchText = {},
            isToggledFullSearch = false
        )
    }
}
