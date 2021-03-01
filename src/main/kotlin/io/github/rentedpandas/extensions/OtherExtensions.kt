package io.github.rentedpandas.extensions

internal fun <T> T.applyIfNotNull(block: (T.() -> Unit)?): T {
    if (block != null)
        apply(block)
    return this
}