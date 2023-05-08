// Used for codelist.db file generation. It joins multiple code lists from official websites.
@file:DependsOn("org.xerial:sqlite-jdbc:3.41.2.1")
@file:DependsOn("com.github.doyaaaaaken:kotlin-csv-jvm:1.9.0")

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.net.URL
import java.sql.DriverManager
import kotlin.io.path.Path
import kotlin.io.path.deleteIfExists

val outputDatabaseFile = Path("app/src/main/assets/codelist.db")

println("Starting codelist import...")

// Remove previous DB
outputDatabaseFile.deleteIfExists()

// Download all tables
val lists = buildMap {
    // MetaIS
    put("CL000056", downloadCodeList(getMetaisCodeListUrl("CL000056"), 12, 13))
    put("CL010108", downloadCodeList(getMetaisCodeListUrl("CL010108"), 12, 13))
    put("CL005205", downloadCodeList(getMetaisCodeListUrl("CL005205"), 12, 13))
    // Other
    put("CL010112", downloadCodeList("https://slovak.statistics.sk/wps/portal/ext/metadata/dials/!ut/p/z1/jZHJjsIwEES_hS9wGdtJOHYQchoCSgxh8QXlhCKxzGHE94PYpBmETd9aXU9VpRZerIU_tudu1_52p2O7v-4bn2zrlLM8lwRMJ0Ow4qLOyUkkWqz-CrKZG4EXVFk31hLaCH89L8vqccbcgGwltasrKE4efEDgA_789B9aKnRaAllpDZiKxg1qpUAqyL_y48MQvuMDAUM8mQif3PlAvDBP5p9_4_rgmWOT6oWEVW_8myDa398koQ_EOvjYk30sxc-huc8aHe96vQuqrhTD/dz/d5/L2dBISEvZ0FBIS9nQSEh/p0/IZ7_Q7I8BB1A00HCB0IR6PUKPT3031=CZ6_Q7I8BB1A00MKC0I3IHQBAR1064=NEcodeListVersion!1=fileType!CSV=codeListCode!CL010112=actionCommand!getCodeListDataInFile==/", 1, 2, delimiter = '|'))
}

println("Download done, creating database.")

// Insert into database
DriverManager.getConnection("jdbc:sqlite:$outputDatabaseFile").use { connection ->
    connection.createStatement().use { statement ->
        statement.executeUpdate("CREATE TABLE codelist (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "list_name TEXT NOT NULL, code INTEGER NOT NULL, value TEXT NOT NULL)")

        lists.forEach { (listName, data) ->
            data.forEach { (key, value) ->
                statement.executeUpdate("INSERT INTO codelist VALUES (null, '$listName', $key, '$value')")
            }
        }
    }
}

println("Import finished.")

fun getMetaisCodeListUrl(codeListName: String): String =
    "https://metais.vicepremier.gov.sk/codelistrepository/codelists/codelistheaders/$codeListName/download?type=CSV"

fun downloadCodeList(url: String, codeIndex: Int, valueIndex: Int, delimiter: Char = ','): Map<String, String> {
    return csvReader { this.delimiter = delimiter }.readAll(URL(url).openStream()).drop(1)
        .filter { it[codeIndex].matches("[0-9]+".toRegex()) }
        .associate { it[codeIndex] to it[valueIndex] }
}
