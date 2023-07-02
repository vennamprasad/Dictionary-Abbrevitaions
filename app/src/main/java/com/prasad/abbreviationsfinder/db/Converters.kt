package com.prasad.abbreviationsfinder.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.reflect.TypeToken
import com.prasad.abbreviationsfinder.utils.JsonParser

@ProvidedTypeConverter
class Converters( private val jsonParser: JsonParser
) {

    @TypeConverter
    fun fromJson(json: String): List<String> {
        return jsonParser.fromJson<List<String>>(
            json,
            object : TypeToken<List<String>>(){}.type
        ) ?: listOf()
    }

    @TypeConverter
    fun toJson(meanings: List<String>): String {
        return jsonParser.toJson(
            meanings,
            object : TypeToken<List<String>>(){}.type
        ) ?: "[]"
    }
}