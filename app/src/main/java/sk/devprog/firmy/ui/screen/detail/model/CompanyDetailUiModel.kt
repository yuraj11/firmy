package sk.devprog.firmy.ui.screen.detail.model

import androidx.compose.ui.text.AnnotatedString
import sk.devprog.firmy.util.TextResource

data class CompanyDetailUiModel(
    val id: Int,
    val basicTab: List<CompanyDetailSectionUiModel>,
    val activitiesTab: List<CompanyDetailSectionUiModel>,
    val peopleTab: List<CompanyDetailSectionUiModel>,
    val financesTab: List<CompanyDetailSectionUiModel>,
    val otherTab: List<CompanyDetailSectionUiModel>,
) {
    val allSections get() = basicTab + activitiesTab + peopleTab + financesTab + otherTab
}

data class CompanyDetailSectionUiModel(
    val title: TextResource,
    val allItems: List<CompanyDetailItemUiModel>,
) {
    val validItems: List<CompanyDetailItemUiModel> get() = allItems.filter { it.validTo == null }
}

data class CompanyDetailItemUiModel(
    val text: AnnotatedString,
    val validFrom: String? = null,
    val validTo: String? = null,
)
