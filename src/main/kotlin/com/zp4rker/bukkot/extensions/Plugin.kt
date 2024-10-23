package com.zp4rker.bukkot.extensions

import org.bukkit.plugin.Plugin
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.scheduler.BukkitTask

/**
 * Run a regular [BukkitRunnable]
 *
 * @param delay ticks before task runs. Set to 0 to run immediately
 * @param async whether or not to run in async
 * @param task the task to run in the [BukkitRunnable]
 *
 * @see BukkitRunnable.runTask
 * @see BukkitRunnable.runTaskLater
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

/**
 * Run a timer [BukkitRunnable]
 *
 * @param period ticks between runs
 *
 * @see runTask
 * @see BukkitRunnable.runTaskTimer
 */
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

/**
 * Run a timer [BukkitRunnable] limited amount of times
 *
 * @param repeat amount of times to repeat task. Specified as range (useful for using within task)
 * @param task the task to run in the [BukkitRunnable]. Has access to repeat number
 *
 * @see IntProgression
 * @see runTaskTimer
 */
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

/**
 * Class to simplify [BukkitRunnable] lambdas
 */
internal class LambdaRunnable(private val task: BukkitRunnable.() -> Unit) : BukkitRunnable() {
    override fun run() {
        task()
    }
}