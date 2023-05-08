package sk.devprog.firmy.data.repository

import kotlinx.coroutines.flow.Flow
import sk.devprog.firmy.data.database.AppDatabase
import sk.devprog.firmy.data.database.entity.SavedCompanyEntity
import javax.inject.Inject

/**
 * Provides favorite entities.
 */
class FavoritesRepository @Inject constructor(
    private val appDatabase: AppDatabase
) {
    fun getAllFavorites(): Flow<List<SavedCompanyEntity>> =
        appDatabase.savedCompaniesDao().findAll()

    suspend fun checkIfExists(id: Int) =
        appDatabase.savedCompaniesDao().checkIfExists(id)

    suspend fun addFavorite(entity: SavedCompanyEntity) =
        appDatabase.savedCompaniesDao().insert(entity)

    suspend fun deleteFavorite(id: Int) =
        appDatabase.savedCompaniesDao().delete(id)
}
