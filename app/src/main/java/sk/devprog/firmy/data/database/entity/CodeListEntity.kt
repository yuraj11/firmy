package sk.devprog.firmy.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "codelist")
data class CodeListEntity(
    @PrimaryKey
    @ColumnInfo("id")
    val id: Int,

    @ColumnInfo("list_name")
    val listName: String,

    @ColumnInfo("code")
    val code: Int,

    @ColumnInfo("value")
    val value: String,
)
