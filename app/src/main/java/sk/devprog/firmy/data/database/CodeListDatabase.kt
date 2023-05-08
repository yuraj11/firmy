package sk.devprog.firmy.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import sk.devprog.firmy.data.database.dao.CodeListDao
import sk.devprog.firmy.data.database.entity.CodeListEntity

/**
 * Database for pre-populated code list.
 */
@Database(entities = [CodeListEntity::class], version = 1, exportSchema = false)
abstract class CodeListDatabase : RoomDatabase() {
    abstract fun codeListDao(): CodeListDao
}
