package sample.helper

import kotlin.io.path.Path

object FilePathHelper {
    fun resourcePath() = "src/main/resources"

    fun docImagesPath() = docBasePath() + "/images"

    fun docHTMLPath() = docBasePath() + "/html"

    fun docRawPath() = docBasePath() + "/raw"

    private fun docBasePath() = Path(System.getProperty("user.dir"), "docs/plot").toString()
}
