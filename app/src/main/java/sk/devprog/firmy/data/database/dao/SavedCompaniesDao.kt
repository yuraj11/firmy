package sk.devprog.firmy.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import sk.devprog.firmy.data.database.entity.SavedCompanyEntity

/**
 * User saved (favorite) companies DAO.
 */
@Dao
interface SavedCompaniesDao {
    @Query("SELECT * FROM saved_company")
    fun findAll(): Flow<List<SavedCompanyEntity>>

    @Query("SELECT EXISTS (SELECT 1 FROM saved_company WHERE id = :id)")
    suspend fun checkIfExists(id: Int): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: SavedCompanyEntity)

    @Query("DELETE FROM saved_company WHERE id = :id")
    suspend fun delete(id: Int)
}
