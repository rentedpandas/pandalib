package io.github.rentedpandas.itemstack

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Creates a new [ItemStack]
 */
inline fun itemStack(material: Material, builder: ItemStack.() -> Unit) = ItemStack(material).apply(builder)

/**
 * [ItemMeta] builder.
 */
inline fun <reified T : ItemMeta> ItemStack.meta(builder: T.() -> Unit) {
    val curMeta = itemMeta as? T
    itemMeta = if (curMeta != null) {
        curMeta.apply(builder)
        curMeta
    } else {
        itemMeta(type, builder)
    }
}

/**
 * @see meta
 */
@JvmName("meta1")
inline fun ItemStack.meta(builder: ItemMeta.() -> Unit) = meta<ItemMeta>(builder)

/**
 * Resets item meta and recreates item meta builder.
 */
inline fun <reified T : ItemMeta> ItemStack.setMeta(builder: T.() -> Unit) {
    itemMeta = itemMeta(type, builder)
}

/**
 * @see setMeta
 */
@JvmName("setMeta1")
inline fun ItemStack.setMeta(builder: ItemMeta.() -> Unit) = setMeta<ItemMeta>(builder)

/**
 * Creates a new [ItemMeta] instance of the given material for building.
 * @param T meta type
 */
inline fun <reified T : ItemMeta> itemMeta(material: Material, builder: T.() -> Unit): T? {
    val meta = Bukkit.getItemFactory().getItemMeta(material)
    return if (meta is T) meta.apply(builder) else null
}

/** @see itemMeta */
@JvmName("itemMeta1")
inline fun itemMeta(material: Material, builder: ItemMeta.() -> Unit) = itemMeta<ItemMeta>(material, builder)

/**
 * Sets the lore of the item.
 */
inline fun ItemMeta.setLore(builder: ItemMetaLoreBuilder.() -> Unit) {
    lore = ItemMetaLoreBuilder().apply(builder).lorelist
}

/**
 * Adds new lines to the lore of the item stack.
 */
inline fun ItemMeta.addLore(builder: ItemMetaLoreBuilder.() -> Unit) {
    val newLore = lore ?: mutableListOf<String>()
    newLore.addAll(ItemMetaLoreBuilder().apply(builder).lorelist)
    lore = newLore
}

/**
 * Lore builder which uses an [ArrayList] under the hood.
 * It exists to provide overloaded operator functions.
 */
class ItemMetaLoreBuilder {
    val lorelist = ArrayList<String>()
    operator fun String.unaryPlus() {
        lorelist += this
    }
}

/**
 * Add a new [ItemFlag] to the item flags.
 */
fun ItemMeta.flag(itemFlag: ItemFlag) = addItemFlags(itemFlag)

/**
 * Add more than one [ItemFlag]s to the item flags.
 */
fun ItemMeta.flags(vararg itemFlag: ItemFlag) = addItemFlags(*itemFlag)

/**
 * Removes a [ItemFlag] from the item flags.
 */
fun ItemMeta.removeFlag(itemFlag: ItemFlag) = removeItemFlags(itemFlag)

/**
 * Removes more than one [ItemFlag]s from the item flags.
 */
fun ItemMeta.removeFlags(vararg itemFlag: ItemFlag) = removeItemFlags(*itemFlag)

/**
 * Get and set the display name of an item.
 */
var ItemMeta.name: String?
    get() = if (hasDisplayName()) displayName else null
    set(value) = setDisplayName(if (value == null || value == "") " " else value)

/**
 * Get and set the custom model data of an item.
 */
var ItemMeta.customModel: Int?
    get() = if (hasCustomModelData()) customModelData else null
    set(value) = setCustomModelData(value)

/**
 * Get and set the localized name of an item.
 */
var ItemMeta.localName: String
    get() = localizedName
    set(value) = setLocalizedName(value)