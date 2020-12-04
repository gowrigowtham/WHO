package com.arcgis.carousel.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.arcgis.network.ApiService
import com.arcgis.network.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val apiService: ApiService) : ViewModel() {


    fun getUsers(countryCode: String ) = liveData(Dispatchers.IO) {

        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = apiService.getDetails("ISO_2_CODE"+"="+"'"+countryCode+"'","CumCase%2CCumDeath","date_epicrv+DESC","1","pjson")))
        }catch (exception: Exception){
            emit(Resource.error(data = null, message = exception.message ?: "Error occured"))
        }
    }


}