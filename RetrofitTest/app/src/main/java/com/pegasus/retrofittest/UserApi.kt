package com.pegasus.retrofittest

import retrofit2.Call
import retrofit2.http.GET

interface UserApi {
    @GET("api")
    fun getUser(): Call<User>
}