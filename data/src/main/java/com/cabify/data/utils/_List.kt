package com.cabify.data.utils

fun <T, R> List<T>.transform(transform: (T) -> R): List<R> {
    val ret = mutableListOf<R>()
    this.forEach { ret.add(transform(it)) }
    return ret.toList()
}