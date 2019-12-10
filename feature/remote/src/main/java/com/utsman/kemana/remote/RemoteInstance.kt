package com.utsman.kemana.remote

import com.utsman.kemana.base.REMOTE_URL
import io.reactivex.Flowable
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RemoteInstance {

    // 124 000 715 7721

    @POST("/api/v1/driver/")
    fun insertDriver(@Body driver: Driver): Flowable<Responses>

    @GET("/api/v1/driver/active")
    fun getAllDriver(): Flowable<Responses>

    @GET("/api/v1/driver/{id}")
    fun getDriver(@Path("id") id: String): Flowable<Responses>

    @DELETE("/api/v1/driver/{id}")
    fun deleteDriver(@Path("id") id: String): Flowable<Responses>

    @DELETE("/api/v1/driver")
    fun deleteDriverByEmail(
        @Query("email") email: String
    ): Flowable<Responses>

    @PUT("/api/v1/driver/{id}")
    fun editDriver(
        @Path("id") id: String,
        @Body position: Position
    ): Flowable<Responses>

    @PUT("/api/v1/driver")
    fun editDriverByEmail(
        @Query("email") email: String,
        @Body position: Position
    ) : Flowable<Responses>

    companion object {

        fun create(): RemoteInstance {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(REMOTE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()

            return retrofit.create(RemoteInstance::class.java)
        }

    }
}