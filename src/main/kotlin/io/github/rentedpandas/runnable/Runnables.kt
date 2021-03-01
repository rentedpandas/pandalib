package io.github.rentedpandas.runnable

import io.github.rentedpandas.plugin.PANDAINSTANCE
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

internal object RunnableHolder : AutoCloseable {

    /**
     * [BukkitRunnable] for tracking the responsible runnable.
     * [Pair] of callback for the endCallback code and [Boolean]
     * for holding the information if the endCallback is safe
     * or not.
     */
    private val runnableEndCallbacks = HashMap<BukkitRunnable, Pair<() -> Unit, Boolean>>()

    override fun close() {
        runnableEndCallbacks.values.forEach { if (it.second) it.first.invoke() }
        runnableEndCallbacks.clear()
    }

    fun add(runnable: BukkitRunnable, callback: () -> Unit, safe: Boolean) =
        runnableEndCallbacks.put(runnable, Pair(callback, safe))

    fun remove(runnable: BukkitRunnable) = runnableEndCallbacks.remove(runnable)
    fun activate(runnable: BukkitRunnable) = runnableEndCallbacks.remove(runnable)?.first?.invoke()

}

abstract class PandaRunnable(
    var counterUp: Long? = null,
    var counterDownToOne: Long? = null,
    var counterDownToZero: Long? = null
) : BukkitRunnable()

/**
 * Starts a new BukkitRunnable.
 *
 * @param sync if the runnable should run sync (true) or async (false)
 * @param howOften how many times the task should be executed - null for infinite execution
 * @param delay the delay (in ticks) until the first execution of the task
 * @param period at which interval (in ticks) the task should be repeated
 * @param safe if the endCallback of the runnable should always be executed,
 * even if the server shuts down or the runnable ends prematurely
 * @param endCallback code that should always be executed when the runnable ends
 * @param runnable the runnable which should be executed each repetition
 *
 * @return [PandaRunnable]
 */
fun task(
    sync: Boolean = true,
    delay: Long = 0,
    period: Long? = null,
    howOften: Long? = null,
    safe: Boolean = false,
    endCallback: (() -> Unit)? = null,
    runnable: ((PandaRunnable) -> Unit)? = null
): PandaRunnable? {

    if (howOften != null && howOften == 0L) return null

    val bukkitRunnable = object : PandaRunnable() {

        private var curCounter = 0L

        override fun run() {

            var ranOut = false
            if (howOften != null) {

                counterDownToOne = howOften - curCounter
                counterDownToZero = counterDownToOne?.minus(1)

                curCounter++
                if (curCounter >= howOften)
                    ranOut = true

                counterUp = curCounter

            }

            runnable?.invoke(this)

            if (ranOut) cancel()

            if (isCancelled) {
                if (safe || ranOut)
                    RunnableHolder.activate(this)
                else
                    RunnableHolder.remove(this)
            }

        }

    }

    if (endCallback != null) RunnableHolder.add(bukkitRunnable, endCallback, safe)

    if (period != null)
        if (sync) bukkitRunnable.runTaskTimer(PANDAINSTANCE, delay, period)
        else bukkitRunnable.runTaskTimerAsynchronously(PANDAINSTANCE, delay, period)
    else
        if (sync) bukkitRunnable.runTaskLater(PANDAINSTANCE, delay)
        else bukkitRunnable.runTaskLaterAsynchronously(PANDAINSTANCE, delay)

    return bukkitRunnable

}

/**
 * Executes the given [runnable] with the given [delay].
 * Either sync or async (specified by the [sync] parameter).
 */
fun runTaskLater(delay: Long, sync: Boolean = true, runnable: () -> Unit) {
    if (sync)
        Bukkit.getScheduler().runTaskLater(PANDAINSTANCE, runnable, delay)
    else
        Bukkit.getScheduler().runTaskLaterAsynchronously(PANDAINSTANCE, runnable, delay)
}

/**
 * Executes the given [runnable] either
 * sync or async (specified by the [sync] parameter).
 */
fun runTask(sync: Boolean = true, runnable: () -> Unit) {
    if (sync) sync(runnable) else async(runnable)
}

/**
 * Starts a synchronous task.
 */
fun sync(runnable: () -> Unit) = Bukkit.getScheduler().runTask(PANDAINSTANCE, runnable)

/**
 * Starts an asynchronous task.
 */
fun async(runnable: () -> Unit) = Bukkit.getScheduler().runTaskAsynchronously(PANDAINSTANCE, runnable)