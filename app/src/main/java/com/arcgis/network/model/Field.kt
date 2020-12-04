package com.arcgis.network.model

import android.os.Parcelable
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Field(
@SerializedName("name")
@Expose
 val name: String? = null,

@SerializedName("type")
@Expose
 val type: String? = null,

@SerializedName("alias")
@Expose
 val alias: String? = null,

@SerializedName("sqlType")
@Expose
 val sqlType: String? = null,

@SerializedName("domain")
@Expose
 val domain: String? = null,

@SerializedName("defaultValue")
@Expose
 val defaultValue: String? = null,
): Parcelable