package com.arcgis.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UniqueIdField(

    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("isSystemMaintained")
    @Expose
    var isSystemMaintained: Boolean
) : Parcelable