package com.rokoblak.trendinggit.data.service.api

import com.rokoblak.trendinggit.data.service.api.model.GithubSearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubApi {
    @GET("search/repositories?q=language=+sort:stars")
    suspend fun searchRepositories(
        @Query("page") page: Int
    ): Response<GithubSearchResponse>
}
