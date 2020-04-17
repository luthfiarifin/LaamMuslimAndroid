package com.laam.laammuslim.data.api

import com.laam.laammuslim.data.model.GithubProfileResponse
import com.laam.laammuslim.data.model.MuslimSalatDailyResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubAPI {

    @GET("users/luthfiarifin")
    fun getUserProfile(): Single<GithubProfileResponse>
}