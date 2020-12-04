package com.arcgis.network


import com.arcgis.network.model.CountryCode
import com.arcgis.network.model.User
import okhttp3.ResponseBody
import retrofit2.http.*

interface ApiService {
    @GET("query?")
    suspend fun getDetails(
        @Query( "where", encoded = true) where: String,
        @Query("outFields", encoded = true) outFields: String,
        @Query("orderByFields", encoded = true) orderByFields: String,
        @Query("resultRecordCount") resultRecordCount: String,
        @Query("f") f: String
    ): User

    @GET("json")
    suspend fun getCountryCode(): CountryCode
}