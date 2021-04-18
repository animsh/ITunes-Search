package com.animsh.itunessearch.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * Created by animsh on 4/18/2021.
 */
@Dao
interface ITunesSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(iTunesSearchEntity: ITunesSearchEntity)

    @Query("SELECT * FROM itunes_search_table ORDER BY id ASC")
    fun readSearch(): Flow<List<ITunesSearchEntity>>
}