package sample.tasks

import org.jetbrains.kotlinx.dataframe.api.add
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.count
import org.jetbrains.kotlinx.dataframe.api.cumSum
import org.jetbrains.kotlinx.dataframe.api.groupBy
import org.jetbrains.kotlinx.dataframe.api.select
import org.jetbrains.kotlinx.dataframe.api.sortBy
import org.jetbrains.kotlinx.dataframe.io.DisplayConfiguration
import org.jetbrains.kotlinx.dataframe.io.toStandaloneHTML
import org.jetbrains.kotlinx.kandy.dsl.plot
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.line
import sample.dataframe.PocketArchiveDataFrame
import sample.extentions.inJST
import sample.helper.FilePathHelper
import sample.helper.log
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime
import kotlin.io.path.Path

class SampleTask {
    companion object {
        private val timeReadInDateTime by column<LocalDate>()
        private val timeReadInYear by column<Int>()
        private val timeReadInMonth by column<Int>()
        private val timeReadInYearMonthStr by column<String>()
        private val readCount by column<Int>()
    }

    fun execute() {
        // Load raw data as DataFrame
        val df = PocketArchiveDataFrame.load()

        // Analyze
        val result =
            df.add(timeReadInDateTime) {
                ZonedDateTime.ofInstant(Instant.ofEpochSecond(PocketArchiveDataFrame.Column.timeRead()), ZoneId.of("UTC"))
                    .inJST()
                    .toLocalDate()
            }.add(timeReadInYear) {
                timeReadInDateTime().year
            }.add(timeReadInMonth) {
                timeReadInDateTime().monthValue
            }.add(timeReadInYearMonthStr) {
                "${timeReadInDateTime().year}/${timeReadInDateTime().monthValue}"
            }.select {
                timeReadInYear and
                    timeReadInMonth and
                    timeReadInYearMonthStr
            }.groupBy { timeReadInYear and timeReadInMonth and timeReadInYearMonthStr }
                .sortBy { timeReadInYear and timeReadInMonth }
                .aggregate { count() into readCount }
                .also { it.log() }
                .cumSum { readCount }.concat()
                .also { it.log() }

        // Plot
        val plot =
            result.plot {
                line {
                    x(timeReadInYearMonthStr)
                    y(readCount)
                }
                layout {
                    size = 1000 to 800
                }
            }

        // save
        result.toStandaloneHTML(
            DisplayConfiguration(rowsLimit = null),
        ).writeHTML(Path("${FilePathHelper.docRawPath()}/${this::class.java.simpleName}.html"))

        plot.save(filename = "${this::class.java.simpleName}.svg", path = FilePathHelper.docImagesPath())
        plot.save(filename = "${this::class.java.simpleName}.html", path = FilePathHelper.docHTMLPath())
    }
}
