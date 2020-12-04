package com.arcgis.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data  class Attributes (

    @SerializedName("CumCase")
    @Expose
     val cumCase: Int? = null,

    @SerializedName("CumDeath")
    @Expose
     val cumDeath: Int? = null,

    @SerializedName("OBJECTID")
    @Expose
     val oBJECTID: Int? = null

    ): Parcelable