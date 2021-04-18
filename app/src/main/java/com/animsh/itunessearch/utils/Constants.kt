package com.animsh.itunessearch.utils

/**
 * Created by animsh on 4/18/2021.
 */
class Constants {
    companion object {
        const val BASE_URL = "https://itunes.apple.com/"
        const val ENTITY_TYPE_MUSIC_TRACK = "musicTrack"

        const val PREFERENCES_NAME = "itunes_preferences"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

        // Room Database
        const val ITUNES_SEARCH_TABLE = "itunes_search_table"
        const val ITUNES_SEARCH_DATABASE = "itunes_search_database"
    }
}