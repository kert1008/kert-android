package com.pegasus.rxtest

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    companion object {
        const val BASE_URL = "https://api.github.com"
    }

    @GET("users/{username}")
    fun getUser(@Path("username") user: String): Observable<User>
}