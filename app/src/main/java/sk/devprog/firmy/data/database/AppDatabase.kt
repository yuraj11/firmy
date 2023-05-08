package sk.devprog.firmy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sk.devprog.firmy.data.database.dao.SavedCompaniesDao
import sk.devprog.firmy.data.database.entity.SavedCompanyEntity

/**
 * Database for application related data.
 */
@Database(entities = [SavedCompanyEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun savedCompaniesDao(): SavedCompaniesDao
}
