package com.zp4rker.bukkot.sample

import com.zp4rker.bukkot.api.BlockingFunction
import com.zp4rker.bukkot.extensions.runTask
import com.zp4rker.bukkot.listener.expect
import com.zp4rker.bukkot.listener.expectBlocking
import com.zp4rker.bukkot.listener.on
import org.bukkit.event.server.ServerCommandEvent
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.TimeUnit

/**
 * @author zp4rker
 */
class SamplePlugin : JavaPlugin() {
    @OptIn(BlockingFunction::class)
    override fun onEnable() {
        try {
            Class.forName("com.zp4rker.bukkot.Bukkot")
        } catch (_: ClassNotFoundException) {
            logger.severe("Bukkot is not installed! Disabling plugin")
            server.pluginManager.disablePlugin(this)
            return
        }

        // Inline listener - regular
        this.on<ServerCommandEvent> {
            logger.info("Console ran: /${it.command}")
        }

        // Inline listener - regular #2
        this.on<ServerCommandEvent>(predicate = { it.command.startsWith("say", true) }) {
            logger.info("Console said: ${it.command.substring(4)}")
        }

        // Inline listener - expect
        this.expect<ServerCommandEvent> {
            logger.info("You should only see this once")
        }

        // Inline listener - expect #2
        this.expect<ServerCommandEvent>(amount = 2) {
            logger.info("You should see this twice")
        }

        // Inline listener - expect #3
        this.expect<ServerCommandEvent>(timeout = TimeUnit.SECONDS.toMillis(10), timeoutAction = {
            logger.info("You should see this if console did not issue a command within 10 seconds")
        }) {
            logger.info("You should see this if console issues a command within 10 seconds")
        }

        // Inline listener - expectBlocking
        this.runTask(async = true) {
            logger.info("This is part 1")
            this@SamplePlugin.expectBlocking<ServerCommandEvent> {
                logger.info("This is part 2")
            }
            logger.info("This is part 3")
        }

        // Inline listener - expectBlocking #2
        this.runTask(async = true) {
            logger.info("This is part 1 of 3")
            this@SamplePlugin.expectBlocking<ServerCommandEvent>(
                timeout = TimeUnit.SECONDS.toMillis(10),
                timeoutAction = {
                    logger.info("This is part 2 of 3 - but as a timeout")
                }) {
                logger.info("This is part 2 of 3")
            }
            logger.info("This is part 3 of 3")
        }
    }
}