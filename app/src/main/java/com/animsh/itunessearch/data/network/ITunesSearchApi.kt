package com.animsh.itunessearch.data.network

import com.animsh.itunessearch.models.ITunesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by animsh on 4/18/2021.
 */
interface ITunesSearchApi {

    @GET("search")
    suspend fun getResult(
        @Query("term") searchTerm: CharSequence
    ): Response<ITunesResponse>
}