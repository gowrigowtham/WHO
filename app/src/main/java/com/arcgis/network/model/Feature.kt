package com.arcgis.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Feature(
    @SerializedName("attributes")
    @Expose
     val attributes: Attributes? = null
) : Parcelable