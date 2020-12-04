package com.arcgis.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SpatialReference (

    @SerializedName("wkid")
    @Expose
     val wkid: Int? = null,

    @SerializedName("latestWkid")
    @Expose
     val latestWkid: Int? = null
    ) : Parcelable