package sample.helper

import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.print
import org.jetbrains.kotlinx.dataframe.api.schema

fun <T> DataFrame<T>.log() {
    println("[INFO] -------- Schema --------")
    println(this.schema())

    println("[INFO] -------- Actual DataFrame Content --------")
    this.print()
}
