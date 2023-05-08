package sk.devprog.firmy.ui.screen.more

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.BuildConfig
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.ui.theme.Typography

@Composable
fun MoreScreen(
    onLicensesClick: () -> Unit,
    onOpenLinkClick: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.Center, modifier = Modifier
        .fillMaxSize()
        .verticalScroll(rememberScrollState())
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_app_logo),
                contentDescription = null
            )
            Text(
                stringResource(
                    id = R.string.more_app_title,
                    stringResource(id = R.string.app_name),
                    BuildConfig.VERSION_NAME
                ),
                style = Typography.headlineMedium,
                textAlign = TextAlign.Center
            )
            Text(
                stringResource(id = R.string.more_app_description),
                textAlign = TextAlign.Center
            )
        }
        Column(modifier = Modifier.padding(16.dp)) {
            TextButton(onClick = onLicensesClick, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(id = R.string.more_link_licenses))
            }
            TextButton(
                onClick = { onOpenLinkClick("https://github.com/yuraj11/firmy") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.more_link_github))
            }
            TextButton(
                onClick = { onOpenLinkClick("market://details?id=${BuildConfig.APPLICATION_ID}") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(id = R.string.more_link_play_store))
            }
            Divider(modifier = Modifier.padding(top = 16.dp))
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.more_license),
                style = Typography.labelSmall
            )
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(id = R.string.more_author),
                style = Typography.labelSmall
            )
        }
    }
}

@Preview
@Composable
private fun MoreScreenPreview() {
    AppTheme {
        MoreScreen(
            onLicensesClick = {},
            onOpenLinkClick = {}
        )
    }
}
