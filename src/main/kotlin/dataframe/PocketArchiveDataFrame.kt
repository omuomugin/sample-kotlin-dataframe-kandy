package sample.dataframe

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.io.ColType
import org.jetbrains.kotlinx.dataframe.io.readCSV
import sample.helper.log

object PocketArchiveDataFrame {
    object Column {
        val id by column<String>()
        val favorite by column<Int>()
        val timeAdded by column<Long>()
        val timeRead by column<Long>()
    }

    fun load(): DataFrame<*> {
        return DataFrame.readCSV(
            "src/main/resources/pocket_archive.csv",
            delimiter = ',',
            header =
                listOf(
                    Column.id.name(),
                    Column.favorite.name(),
                    Column.timeAdded.name(),
                    Column.timeRead.name(),
                ),
            colTypes =
                mapOf(
                    Column.id.name() to ColType.String,
                    Column.favorite.name() to ColType.Int,
                    Column.timeAdded.name() to ColType.Long,
                    Column.timeRead.name() to ColType.Long,
                ),
        ).also { it.log() }
    }
}
