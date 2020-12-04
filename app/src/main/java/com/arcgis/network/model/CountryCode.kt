package com.arcgis.network.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


data class CountryCode(
    @SerializedName("status")
    @Expose
    var status: String,
    @SerializedName("country")
    @Expose
    var country: String,
    @SerializedName("region")
    @Expose
    var region: String,
    @SerializedName("regionName")
    @Expose
    var regionName: String,
    @SerializedName("city")
    @Expose
    var city: String,
    @SerializedName("zip")
    @Expose
    var zip: String,
    @SerializedName("lat")
    @Expose
    var lat: String,
    @SerializedName("lon")
    @Expose
    var lon: String,
    @SerializedName("timezone")
    @Expose
    var timezone: String,
    @SerializedName("isp")
    @Expose
    var isp: String,
    @SerializedName("org")
    @Expose
    var org: String,
    @SerializedName("query")
    @Expose
    var query: String,
    @SerializedName("as")
    @Expose
    var asd: String,
    @SerializedName("countryCode")
    @Expose
    var countryCode: String
)
