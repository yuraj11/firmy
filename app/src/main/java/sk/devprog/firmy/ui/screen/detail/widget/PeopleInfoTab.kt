package sk.devprog.firmy.ui.screen.detail.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailItemUiModel
import sk.devprog.firmy.ui.screen.detail.model.CompanyDetailSectionUiModel
import sk.devprog.firmy.ui.theme.AppTheme
import sk.devprog.firmy.util.toTextResource

@Composable
fun PeopleInfoTab(items: List<CompanyDetailSectionUiModel>) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.forEach { section ->
            DetailSection(section, spacingBetweenItems = 12.dp)
        }
    }
}

@Preview
@Composable
private fun PeopleInfoTabPreview() {
    val items = listOf(
        CompanyDetailSectionUiModel(
            "Title".toTextResource(),
            listOf(CompanyDetailItemUiModel(AnnotatedString("Test")))
        )
    )
    AppTheme {
        PeopleInfoTab(items)
    }
}
