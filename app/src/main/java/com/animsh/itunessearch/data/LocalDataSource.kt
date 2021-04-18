package com.animsh.itunessearch.data

import com.animsh.itunessearch.data.database.ITunesSearchDao
import com.animsh.itunessearch.data.database.ITunesSearchEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by animsh on 4/18/2021.
 */
class LocalDataSource @Inject constructor(
    private val iTunesSearchDao: ITunesSearchDao
) {
    fun readDatabase(): Flow<List<ITunesSearchEntity>> {
        return iTunesSearchDao.readSearch()
    }

    suspend fun insertSearch(iTunesSearchEntity: ITunesSearchEntity) {
        iTunesSearchDao.insertSearch(iTunesSearchEntity)
    }
}