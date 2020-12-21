package io.appicenter.data.db

import androidx.room.TypeConverter
import io.appicenter.domain.model.OrderItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

object AppTypeConverters {

    @TypeConverter
    @JvmStatic
    fun toDate(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(value: Date?): Long? {
        return value?.time
    }

    @TypeConverter
    @JvmStatic
    fun fromOrderList(value: List<OrderItem>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromOrderString(value: String): List<OrderItem> {
        val listType = object : TypeToken<List<OrderItem>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    @JvmStatic
    fun fromStringList(value: List<String>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    @JvmStatic
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }


}