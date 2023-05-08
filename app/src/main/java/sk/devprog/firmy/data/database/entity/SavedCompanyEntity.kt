package sk.devprog.firmy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "saved_company")
data class SavedCompanyEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("created_at")
    val createdAt: Long,

    @ColumnInfo("company_name")
    val companyName: String,

    @ColumnInfo("old_company_name")
    val oldCompanyName: String? = null,

    @ColumnInfo("address")
    val address: String,

    @ColumnInfo("identifier_number")
    val identifierNumber: String,

    @ColumnInfo("establishment")
    val establishment: String,

    @ColumnInfo("termination")
    val termination: String? = null,
)
