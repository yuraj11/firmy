package sk.devprog.firmy.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import sk.devprog.firmy.data.database.entity.CodeListEntity

/**
 * Code list for company related data.
 */
@Dao
interface CodeListDao {
    @Query("SELECT * FROM codelist WHERE list_name LIKE :codeListName")
    suspend fun findAllByCodeListName(codeListName: String): List<CodeListEntity>
}
