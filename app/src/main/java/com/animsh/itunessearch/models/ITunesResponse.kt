package com.animsh.itunessearch.models


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ITunesResponse(
    @SerializedName("results")
    val results: List<Result>
) : Parcelable