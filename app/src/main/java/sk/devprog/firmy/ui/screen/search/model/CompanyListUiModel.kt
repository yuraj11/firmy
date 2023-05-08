package sk.devprog.firmy.ui.screen.search.model

import sk.devprog.firmy.util.TextResource

data class CompanyListUiModel(
    val id: Int,
    val createdAt: Long = 0,
    val companyName: TextResource,
    val oldCompanyName: TextResource? = null,
    val address: TextResource,
    val identifierNumber: TextResource,
    val establishment: TextResource,
    val termination: TextResource? = null,
)
