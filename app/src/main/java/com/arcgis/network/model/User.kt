package com.arcgis.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("objectIdFieldName")
    @Expose
    var objectIdFieldName: String,
    @SerializedName("uniqueIdField")
    @Expose
    var uniqueIdField: UniqueIdField,
    @SerializedName("globalIdFieldName")
    @Expose
    var globalIdFieldName: String,
    @SerializedName("geometryType")
    @Expose
    var geometryType: String,
    @SerializedName("spatialReference")
    @Expose
    var spatialReference: SpatialReference,
    @SerializedName("fields")
    @Expose
    var fields: ArrayList<Field> = arrayListOf(),
    @SerializedName("exceededTransferLimit")
    @Expose
    var exceededTransferLimit: Boolean,
    @SerializedName("features")
    @Expose
    var features: ArrayList<Feature> = arrayListOf()
) : Parcelable