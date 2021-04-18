package com.animsh.itunessearch.data

import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

/**
 * Created by animsh on 4/18/2021.
 */
@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {
    val local = localDataSource
    val remote = remoteDataSource
}