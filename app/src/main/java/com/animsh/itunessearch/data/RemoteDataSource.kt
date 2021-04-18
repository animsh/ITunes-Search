package com.animsh.itunessearch.data

import com.animsh.itunessearch.data.network.ITunesSearchApi
import com.animsh.itunessearch.models.ITunesResponse
import retrofit2.Response
import javax.inject.Inject

/**
 * Created by animsh on 4/18/2021.
 */
class RemoteDataSource @Inject constructor(
    private val iTunesSearchApi: ITunesSearchApi
) {
    suspend fun getSearchResult(term: CharSequence): Response<ITunesResponse> {
        return iTunesSearchApi.getResult(term)
    }
}