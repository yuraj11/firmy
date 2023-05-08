package sk.devprog.firmy.ui.common.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.R
import sk.devprog.firmy.ui.common.model.UiError
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.util.toTextResource

@Composable
fun ErrorLayout(error: UiError, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .alpha(0.3f)
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painterResource(id = error.icon),
                modifier = Modifier.size(64.dp),
                contentDescription = null
            )
            Text(text = error.text.asString(), modifier = Modifier.padding(all = 16.dp))
        }
    }
}

@Preview
@Composable
private fun ErrorLayoutPreview() {
    AppTheme {
        ErrorLayout(error = UiError("Error".toTextResource(), R.drawable.ic_outline_wifi_off))
    }
}
