package com.zp4rker.bukkot.listener

import com.zp4rker.bukkot.api.BlockingFunction
import com.zp4rker.bukkot.extensions.unregister
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.runBlocking
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.plugin.Plugin
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

typealias Predicate<T> = (T) -> Boolean

/**
 * Register a continuous listener
 *
 * @param T the event to listen to
 * @param predicate function to determine pre-requisites for listener to invoke action
 * @param priority the listener priority
 * @param ignoreCancelled whether the listener should still trigger for cancelled events
 * @param action the function to be executed in the listener
 */
inline fun <reified T : Event> Plugin.on(
    crossinline predicate: Predicate<T> = { true },
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    listener(predicate) {
        action(it)
    }.register(this, priority, ignoreCancelled)
}

val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

/**
 * Register a limited listener
 *
 * @param T the event to listen to
 * @param amount amount of times this listener should listen for event
 * @param timeout milliseconds before listener times out even if [amount] has not been reached. Set to 0 for no timeout
 * @param timeoutAction the function to run when the listener times out
 *
 * @see on
 */
inline fun <reified T : Event> Plugin.expect(
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    var calls = 0
    val listener = listener(predicate) {
        action(it)
        if (++calls >= amount) {
            unregister()
        }
    }

    if (timeout > 0) {
        scheduler.schedule({
            if (calls < amount) {
                timeoutAction()
                listener.unregister()
            }
        }, timeout, TimeUnit.MILLISECONDS)
    }

    listener.register(this, priority, ignoreCancelled)
}

/**
 * Register a limited listener which blocks the current thread
 *
 * Blocks until all calls are made or the listener times out
 *
 * @param T the event to listen to
 *
 * @see expect
 */
@BlockingFunction
inline fun <reified T : Event> Plugin.expectBlocking(
    crossinline predicate: Predicate<T> = { true },
    amount: Int = 1,
    timeout: Long = 0,
    crossinline timeoutAction: () -> Unit = {},
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
    crossinline action: AnonymousListener<T>.(T) -> Unit
) {
    val channel = Channel<Boolean?>(1)

    var calls = 0
    val listener = listener(predicate) {
        action(it)
        if (++calls >= amount) {
            unregister()
            runBlocking { channel.send(null) }
        }
    }

    if (timeout > 0) {
        scheduler.schedule({
            if (calls < amount) {
                timeoutAction()
                listener.unregister()
                runBlocking { channel.send(null) }
            }
        }, timeout, TimeUnit.MILLISECONDS)
    }

    listener.register(this, priority, ignoreCancelled)

    runBlocking { channel.receive() }
}

