package com.dvt.greensys.weather.app.core.common.util

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlin.test.Test
import kotlin.test.assertEquals

class InstantExtKtTest {
    private val dateString = "2024-09-09T00:00:00+09:00"
    private val datetime = Instant.parse(input = dateString)
    private val timeZone = TimeZone.of("Asia/Tokyo")

    @Test
    fun toString_test() {
        val result = datetime.toString(format = "yyyy-MM-dd", timeZone = timeZone)

        assertEquals("2024-09-09", result)
    }
}
