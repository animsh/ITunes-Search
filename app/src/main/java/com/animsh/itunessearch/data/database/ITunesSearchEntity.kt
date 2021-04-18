package com.animsh.itunessearch.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.animsh.itunessearch.models.ITunesResponse
import com.animsh.itunessearch.utils.Constants.Companion.ITUNES_SEARCH_TABLE

/**
 * Created by animsh on 4/18/2021.
 */
@Entity(tableName = ITUNES_SEARCH_TABLE)
class ITunesSearchEntity(
    val iTunesResponse: ITunesResponse
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}