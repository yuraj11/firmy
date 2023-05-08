package sk.devprog.firmy.ui.screen.search.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sk.devprog.firmy.R
import sk.devprog.firmy.data.model.CodeListId
import sk.devprog.firmy.data.model.company.CompanySearchParameters
import sk.devprog.firmy.ui.theme.AppTheme

@Composable
fun SearchAdvancedForm(
    searchParams: CompanySearchParameters,
    codeList: List<String>,
    updateParams: (CompanySearchParameters) -> Unit,
    requestCodeList: (String) -> Unit,
    startSearch: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 16.dp)
                .verticalScroll(scrollState), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .toggleable(
                        value = searchParams.onlyActive,
                        onValueChange = { updateParams(searchParams.copy(onlyActive = it)) },
                        role = Role.Checkbox
                    )
                    .padding(vertical = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = searchParams.onlyActive, onCheckedChange = null)
                Text(
                    text = stringResource(id = R.string.search_form_only_active),
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
            SearchTextField(
                title = stringResource(id = R.string.search_form_ico),
                text = searchParams.identifier.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(identifier = it))
                }
            )
            SearchTextFieldItems(
                title = stringResource(id = R.string.search_form_legal_form),
                text = searchParams.legalForm.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(legalForm = it))
                },
                onClick = { requestCodeList(CodeListId.LEGAL_FORM.listName) },
                items = codeList
            )
            SearchTextFieldItems(
                title = stringResource(id = R.string.search_form_legal_status),
                text = searchParams.legalStatus.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(legalStatus = it))
                },
                onClick = { requestCodeList(CodeListId.LEGAL_STATUS.listName) },
                items = codeList
            )
            SearchTextFieldItems(
                title = stringResource(id = R.string.search_form_source_register),
                text = searchParams.sourceRegister.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(sourceRegister = it))
                },
                onClick = { requestCodeList(CodeListId.SOURCE_REGISTER.listName) },
                items = codeList
            )
            SearchTextField(
                title = stringResource(id = R.string.search_form_municipality),
                text = searchParams.addressMunicipality.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(addressMunicipality = it))
                }
            )
            SearchTextField(
                title = stringResource(id = R.string.search_form_street),
                text = searchParams.addressStreet.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(addressStreet = it))
                }
            )
            SearchTextFieldItems(
                title = stringResource(id = R.string.search_form_main_activity),
                text = searchParams.mainActivity.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(mainActivity = it))
                },
                onClick = { requestCodeList(CodeListId.ACTIVITIES.listName) },
                items = codeList
            )
            SearchTextField(
                title = stringResource(id = R.string.search_form_stakeholder_name),
                text = searchParams.stakeholderPersonGivenName.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(stakeholderPersonGivenName = it))
                }
            )
            SearchTextField(
                title = stringResource(id = R.string.search_form_stakeholder_surname),
                text = searchParams.stakeholderPersonFamilyName.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(stakeholderPersonFamilyName = it))
                }
            )
            SearchTextField(
                title = stringResource(id = R.string.search_form_statutory_name),
                text = searchParams.statutoryBodyGivenName.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(statutoryBodyGivenName = it))
                }
            )
            SearchTextField(
                title = stringResource(id = R.string.search_form_statutory_surname),
                text = searchParams.statutoryBodyFamilyName.orEmpty(),
                updateText = {
                    updateParams(searchParams.copy(statutoryBodyFamilyName = it))
                }
            )
            SearchTextFieldDateRange(
                title = stringResource(id = R.string.search_form_establishment_date),
                startDate = searchParams.establishmentAfter,
                endDate = searchParams.establishmentBefore,
                onUpdateDate = { start, end ->
                    updateParams(searchParams.copy(establishmentAfter = start, establishmentBefore = end))
                }
            )
            SearchTextFieldDateRange(
                title = stringResource(id = R.string.search_form_termination_date),
                startDate = searchParams.terminationAfter,
                endDate = searchParams.terminationBefore,
                onUpdateDate = { start, end ->
                    updateParams(searchParams.copy(terminationAfter = start, terminationBefore = end))
                }
            )
        }

        TextButton(
            onClick = { updateParams(CompanySearchParameters()) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(stringResource(id = R.string.search_clear_filter))
        }

        Button(onClick = { startSearch() }, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(id = R.string.search_start))
        }
    }
}

@Preview
@Composable
private fun SearchAdvancedFormPreview() {
    AppTheme {
        SearchAdvancedForm(
            searchParams = CompanySearchParameters(),
            startSearch = {},
            updateParams = {},
            requestCodeList = {},
            codeList = emptyList()
        )
    }
}
