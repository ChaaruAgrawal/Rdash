package com.assignment.rdash.data

import retrofit2.http.GET

interface RDashAPI {

    @GET("webhook/223a7fe0-4e32-4414-aacf-1bc0ab1c0bba")
    suspend fun getDesignLayouts(): DesignLayouts

}