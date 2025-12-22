package jp.co.greensys.weather.app.core.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

/** InstantをStringに変換 */
fun Instant.toString(format: String, timeZone: TimeZone = TimeZone.currentSystemDefault()): String = runCatching {
    val localDateTime = toLocalDateTime(timeZone = timeZone).toJavaLocalDateTime()
    DateTimeFormatter.ofPattern(format).format(localDateTime)
}.getOrDefault(defaultValue = "")
