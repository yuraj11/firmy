package sk.devprog.firmy.data.repository

import sk.devprog.firmy.data.database.CodeListDatabase
import sk.devprog.firmy.data.database.entity.CodeListEntity
import javax.inject.Inject

/**
 * Provides code lists.
 */
class CodeListRepository @Inject constructor(
    private val codeListDatabase: CodeListDatabase
) {
    suspend fun getCodeListByName(codeListName: String): List<CodeListEntity> =
        codeListDatabase.codeListDao().findAllByCodeListName(codeListName)
}
