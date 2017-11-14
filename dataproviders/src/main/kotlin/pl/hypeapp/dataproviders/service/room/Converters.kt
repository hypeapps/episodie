package pl.hypeapp.dataproviders.service.room

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {

    @TypeConverter
    fun fromArrayList(list: ArrayList<String>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun fromString(string: String): ArrayList<String> {
        val listType = object : TypeToken<ArrayList<String>>() {}.type
        return Gson().fromJson(string, listType)
    }

}
