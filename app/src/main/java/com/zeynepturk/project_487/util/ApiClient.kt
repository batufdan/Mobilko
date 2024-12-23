package com.zeynepturk.project_487.util
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    private var retrofit: Retrofit? = null

    val okHttpClient = OkHttpClient.Builder()
        .hostnameVerifier { _, _ -> true } // Bypass hostname verification (NOT for production!)
        .build()

    fun getClient(): Retrofit {
        if (retrofit == null)
            retrofit = Retrofit.Builder()
                .baseUrl("https://jsonkeeper.com/b/JC0I/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) //retrofit will understand as a converter GSON converter will be used
                .build()

        return retrofit as Retrofit
    }
}