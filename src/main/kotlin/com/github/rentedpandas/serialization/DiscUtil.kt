package com.github.rentedpandas.serialization

import com.github.rentedpandas.runnable.async
import java.io.*
import java.nio.charset.StandardCharsets
import java.util.HashMap
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReadWriteLock
import java.util.concurrent.locks.ReentrantReadWriteLock

object DiscUtil {

    private val locks: HashMap<String, Lock?> = HashMap<String, Lock?>()

    @Throws(IOException::class)
    fun readString(file: File): String {
        val length = file.length().toInt()
        val output = ByteArray(length)
        val inputStream: InputStream = FileInputStream(file)
        var offset = 0
        while (offset < length) {
            offset += inputStream.read(output, offset, length - offset)
        }
        inputStream.close()
        return String(output, StandardCharsets.UTF_8)
    }

    @Throws(IOException::class)
    fun writeBytes(file: File, bytes: ByteArray?) {
        val out = FileOutputStream(file)
        out.write(String(bytes!!, StandardCharsets.UTF_8).toByteArray())
        out.close()
    }

    @Throws(IOException::class)
    fun write(file: File?, content: String) {
        if (file != null) {
            writeBytes(file, utf8(content))
        }
    }

    @Throws(IOException::class)
    fun read(file: File): String {
        return readString(file)
    }

    fun writeCatch(file: File, content: String, sync: Boolean): Boolean {
        val name = file.name
        val lock: Lock?

        if (locks.containsKey(name)) {
            lock = locks[name]
        } else {
            val rwl: ReadWriteLock = ReentrantReadWriteLock()
            lock = rwl.writeLock()
            locks[name] = lock
        }
        if (sync) {
            lock?.lock()
            try {
                file.createNewFile()
                val outputStream: OutputStream = FileOutputStream(file)
                OutputStreamWriter(outputStream, StandardCharsets.UTF_8).use { writer -> writer.write(content) }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                lock?.unlock()
            }
        } else {
            async {
                lock?.lock()
                try {
                    write(file, content)
                } catch (ex: IOException) {
                    ex.printStackTrace()
                } finally {
                    lock?.unlock()
                }
            }
        }
        return true
    }

    fun readCatch(file: File): String? {
        return try {
            read(file)
        } catch (e: IOException) {
            null
        }
    }

    fun utf8(string: String): ByteArray {
        return string.toByteArray(StandardCharsets.UTF_8)
    }

    fun utf8(bytes: ByteArray?): String {
        return String(bytes!!, StandardCharsets.UTF_8)
    }
}