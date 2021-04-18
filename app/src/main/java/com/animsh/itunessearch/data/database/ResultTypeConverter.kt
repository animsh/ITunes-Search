package com.animsh.itunessearch.data.database

import androidx.room.TypeConverter
import com.animsh.itunessearch.models.ITunesResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by animsh on 3/6/2021.
 */
class ResultTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun responseToString(iTunesResponse: ITunesResponse): String {
        return gson.toJson(iTunesResponse)
    }

    @TypeConverter
    fun stringToResponse(data: String): ITunesResponse {
        val listType = object : TypeToken<ITunesResponse>() {}.type
        return gson.fromJson(data, listType)
    }
}