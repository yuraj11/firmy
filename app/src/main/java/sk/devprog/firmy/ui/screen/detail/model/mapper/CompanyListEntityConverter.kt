package sk.devprog.firmy.ui.screen.detail.model.mapper

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import sk.devprog.firmy.data.database.entity.SavedCompanyEntity
import sk.devprog.firmy.ui.screen.search.model.CompanyListUiModel
import sk.devprog.firmy.util.toTextResource
import java.time.Instant
import javax.inject.Inject

class CompanyListEntityConverter @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun mapUiModelToEntity(model: CompanyListUiModel): SavedCompanyEntity =
        SavedCompanyEntity(
            id = model.id,
            createdAt = Instant.now().toEpochMilli(),
            companyName = model.companyName.asString(context),
            oldCompanyName = model.oldCompanyName?.asString(context),
            address = model.address.asString(context),
            identifierNumber = model.identifierNumber.asString(context),
            establishment = model.establishment.asString(context),
            termination = model.termination?.asString(context),
        )

    fun mapEntityToUiModel(model: SavedCompanyEntity): CompanyListUiModel =
        CompanyListUiModel(
            id = model.id,
            createdAt = model.createdAt,
            companyName = model.companyName.toTextResource(),
            oldCompanyName = model.oldCompanyName?.toTextResource(),
            address = model.address.toTextResource(),
            identifierNumber = model.identifierNumber.toTextResource(),
            establishment = model.establishment.toTextResource(),
            termination = model.termination?.toTextResource(),
        )
}
