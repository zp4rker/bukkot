package com.zp4rker.bukkot.extensions

import java.util.concurrent.TimeUnit

/**
 * @author zp4rker
 */
fun TimeUnit.toTicks(d: Long): Long {
    return when (this) {
        TimeUnit.NANOSECONDS -> d / 50000000

        TimeUnit.MICROSECONDS -> d / 50000

        TimeUnit.MILLISECONDS -> d / 50

        TimeUnit.SECONDS -> d * 20

        TimeUnit.MINUTES -> d * 60 * 20

        TimeUnit.HOURS -> d * 60 * 60 * 20

        TimeUnit.DAYS -> d * 24 * 60 * 60 * 20
    }
}

object TickTimeUnit {
    fun toNanos(ticks: Long) = ticks * 50000000
    fun toMicros(ticks: Long) = ticks * 50000
    fun toMillis(ticks: Long) = ticks * 50
    fun toSeconds(ticks: Long) = ticks / 20
    fun toMinutes(ticks: Long) = ticks / 20 / 60
    fun toHours(ticks: Long) = ticks / 20 / 60 / 60
    fun toDays(ticks: Long) = ticks / 20 / 60 / 60 / 24
}