package sk.devprog.firmy.ui.screen.licenses

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LicensesScreen(onBackClicked: () -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(id = R.string.more_licenses_title)) },
            navigationIcon = {
                IconButton(onClick = onBackClicked) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.top_app_bar_back)
                    )
                }
            }
        )
    }) { padding ->
        LibrariesContainer(modifier = Modifier
            .padding(padding)
            .fillMaxSize())
    }

}

@Preview
@Composable
private fun LicensesScreenPreview() {
    AppTheme {
        LicensesScreen(onBackClicked = {})
    }
}
