package io.github.rentedpandas.serialization

import io.github.rentedpandas.extensions.pluginDataFolder
import java.io.File

class Serializer {
    private var data: Boolean
    private var dataFolder: File

    constructor(data: Boolean, dataFolder: File) {
        this.data = data
        this.dataFolder = dataFolder
    }

    constructor(data: Boolean) {
        this.data = data
        dataFolder = pluginDataFolder
    }

    constructor() {
        data = false
        dataFolder = pluginDataFolder
    }

    fun save(instance: Any?, name: String?) {
        Persist(dataFolder).save(data, instance, name!!)
    }

    fun save(instance: Any?) {
        Persist(dataFolder).save(data, instance)
    }

    fun save(instance: Any?, file: File?) {
        Persist(dataFolder).save(data, instance, file!!)
    }

    fun <T> load(def: T, clazz: Class<T>?, name: String?): T {
        return Persist(dataFolder).loadOrSaveDefault(data, def, clazz, name!!)
    }

    fun <T> load(def: T, clazz: Class<T>?, file: File?): T {
        return Persist(dataFolder).loadOrSaveDefault(data, def, clazz, file!!)
    }
}