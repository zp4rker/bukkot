package com.zp4rker.bukkot.extensions

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * @author zp4rker
 */
fun Plugin.runTask(delay: Long = 0, async: Boolean = false, task: BukkitRunnable.() -> Unit): BukkitTask {
    return if (delay > 0) {
        if (async) {
            LambdaRunnable(task).runTaskLaterAsynchronously(this, delay)
        } else {
            LambdaRunnable(task).runTaskLater(this, delay)
        }
    } else {
        if (async) {
            LambdaRunnable(task).runTaskAsynchronously(this)
        } else {
            LambdaRunnable(task).runTask(this)
        }
    }
}

fun Plugin.runTaskTimer(
    delay: Long = 0,
    period: Long = 20,
    async: Boolean = false,
    task: BukkitRunnable.() -> Unit
): BukkitTask {
    return if (async) {
        LambdaRunnable(task).runTaskTimerAsynchronously(this, delay, period)
    } else {
        LambdaRunnable(task).runTaskTimer(this, delay, period)
    }
}

fun Plugin.repeatTask(
    repeat: IntProgression,
    delay: Long = 0,
    period: Long = 20,
    async: Boolean = false,
    task: BukkitRunnable.(Int) -> Unit
): BukkitTask {
    val runnable = object : BukkitRunnable() {
        val i = repeat.iterator()

        override fun run() {
            if (i.hasNext()) task(i.nextInt())
            else cancel()
        }
    }

    return if (async) {
        runnable.runTaskTimerAsynchronously(this, delay, period)
    } else {
        runnable.runTaskTimer(this, delay, period)
    }
}

internal class LambdaRunnable(private val task: BukkitRunnable.() -> Unit) : BukkitRunnable() {
    override fun run() {
        task()
    }
}