package sample.extentions

import java.time.ZoneId
import java.time.ZonedDateTime

fun ZonedDateTime.inJST(): ZonedDateTime = this.withZoneSameInstant(ZoneId.of("Asia/Tokyo"))
