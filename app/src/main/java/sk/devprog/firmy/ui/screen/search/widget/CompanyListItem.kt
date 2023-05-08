package sk.devprog.firmy.ui.screen.search.widget

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.ui.screen.search.model.CompanyListUiModel
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.ui.theme.Typography
import sk.devprog.firmy.util.toTextResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompanyListItem(
    ui: CompanyListUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth(), onClick = { onClick() }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = ui.companyName.asString(), style = Typography.titleMedium)
            ui.oldCompanyName?.let {
                Text(
                    text = it.asString(),
                    style = Typography.labelSmall,
                    textDecoration = TextDecoration.LineThrough,
                    color = Color.Gray
                )
            }
            Spacer(modifier = modifier.height(8.dp))
            Text(text = ui.address.asString(), style = Typography.bodySmall)
            Text(text = ui.identifierNumber.asString(), style = Typography.bodySmall)
            Text(text = ui.establishment.asString(), style = Typography.bodySmall)
            ui.termination?.let {
                Text(text = it.asString(), style = Typography.bodySmall)
            }
        }
    }
}

@Preview
@Composable
private fun CompanyListItemPreview() {
    AppTheme {
        CompanyListItem(
            CompanyListUiModel(
                id = 1,
                companyName = "Company1".toTextResource(),
                oldCompanyName = "Old name".toTextResource(),
                address = "Address".toTextResource(),
                identifierNumber = "100200300".toTextResource(),
                establishment = "10.10.2023".toTextResource(),
                termination = "10.11.2023".toTextResource()
            ),
            onClick = {}
        )
    }
}
