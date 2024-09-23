package com.platon.kotlinmirealabs.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.platon.kotlinmirealabs.models.Source

class Converter {

    @TypeConverter
    fun fromSource(source: Source) = source.name

    @TypeConverter
    fun toSource(name: String) = Source(name, name)
}