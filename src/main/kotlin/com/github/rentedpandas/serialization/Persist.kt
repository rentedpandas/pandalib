package com.github.rentedpandas.serialization

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.github.rentedpandas.extensions.pluginDataFolder
import com.github.rentedpandas.extensions.println
import com.github.rentedpandas.extensions.warn
import com.github.rentedpandas.serialization.DiscUtil.readCatch
import com.github.rentedpandas.serialization.DiscUtil.writeCatch
import org.bukkit.Bukkit
import org.bukkit.command.ConsoleCommandSender
import java.io.*
import java.lang.Exception
import java.lang.reflect.Modifier
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets
import java.nio.file.Files

class Persist(private var dataFolder: File = pluginDataFolder, private val console: ConsoleCommandSender = Bukkit.getConsoleSender()) {

    private val gson: Gson = buildGson().create()
    private val dataGson: Gson = buildDataGson().create()

    private fun buildGson(): GsonBuilder {
        return GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
    }

    private fun buildDataGson(): GsonBuilder {
        return GsonBuilder()
            .disableHtmlEscaping()
            .enableComplexMapKeySerialization()
            .excludeFieldsWithModifiers(Modifier.TRANSIENT, Modifier.VOLATILE)
    }

    fun getName(clazz: Class<*>?): String? {
        return clazz?.simpleName?.toLowerCase()
    }

    fun getName(any: Any?): String? {
        return getName(any?.javaClass)
    }

    fun getName(type: Type): String? {
        return getName(type::class)
    }

    fun getFile(data: Boolean, name: String): File {
        var dataFolder = dataFolder
        if (data) {
            dataFolder = File(dataFolder, "/data")
            dataFolder.mkdirs()
        }
        return File(dataFolder, "$name.json")
    }

    fun getFile(clazz: Class<*>?): File {
        return getFile(false, getName(clazz))
    }

    fun getFile(data: Boolean, any: Any?): File {
        return getFile(data, getName(any))
    }

    fun getFile(type: Type?): File {
        return getFile(false, getName(type))
    }

    fun <T> loadOrSaveDefault(def: T, clazz: Class<T>?): T {
        return loadOrSaveDefault(def, clazz, getFile(clazz))
    }

    fun <T> loadOrSaveDefault(data: Boolean, def: T, clazz: Class<T>?, name: String): T {
        return loadOrSaveDefault(def, clazz, getFile(data, name))
    }

    fun <T> loadOrSaveDefault(data: Boolean, def: T, clazz: Class<T>?, file: File): T {
        return loadOrSaveDefault(def, clazz, file)
    }

    fun <T> loadOrSaveDefault(def: T, clazz: Class<T>?, file: File): T {
        if (!file.exists()) {
            console.println("Creating default: $file")
            this.save(false, def, file)
            return def
        }
        val loaded: T? = this.load(clazz, file)
        if (loaded == null) {
            console.warn("Failed to load file: $file, using default file.")

            val backup = File(file.path + "_backup")
            if (backup.exists()) {
                backup.delete()
            }
            console.warn("Backup created: $backup")
            file.renameTo(backup)
            return def
        }
        return loaded
    }

    fun save(data: Boolean, instance: Any?, name: String): Boolean {
        return save(data, instance, getFile(false, name))
    }

    @JvmOverloads
    fun save(data: Boolean, instance: Any?, file: File = this.getFile(data, instance)): Boolean {
        val gson: Gson = if (data) dataGson else this.gson
        val backupFile = File(file.toPath().toString() + ".backup")
        if (file.exists()) {
            try {
                if (backupFile.exists()) backupFile.delete()
                Files.copy(file.toPath(), backupFile.toPath())
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        val result = writeCatch(file, gson.toJson(instance), true)
        backupFile.delete()
        return result
    }

    fun <T> load(clazz: Class<T>?): T? {
        return load(clazz, getFile(clazz))
    }

    fun <T> load(clazz: Class<T>?, name: String): T? {
        return load(clazz, getFile(false, name))
    }

    fun <T> load(clazz: Class<T>?, file: File): T? {
        val content = readCatch(file)
        if (content == null || !file.exists()) {
            return null
        }
        try {
            val backupFile = File(file.toPath().toString() + ".backup")
            if (backupFile.exists()) backupFile.delete()
            Files.copy(file.toPath(), backupFile.toPath())
            val inputStream: InputStream = FileInputStream(file)
            val result: T = gson.fromJson(InputStreamReader(inputStream, StandardCharsets.UTF_8), clazz)
            if (result != null) {
                backupFile.delete()
            }
            return result
        } catch (ex: Exception) {
            console.warn("${ex.message}")
        }
        return null
    }

    fun <T> load(typeOfT: Type?, name: String): T? {
        return load(typeOfT, getFile(false, name))
    }

    fun <T> load(typeOfT: Type?, file: File): T? {
        val content = readCatch(file)
        if (content == null || !file.exists()) {
            return null
        }
        try {
            val inputStream: InputStream = FileInputStream(file)
            return gson.fromJson(InputStreamReader(inputStream, StandardCharsets.UTF_8), typeOfT) as T
        } catch (ex: Exception) {
            console.warn("${ex.message}")
        }
        return null
    }

}