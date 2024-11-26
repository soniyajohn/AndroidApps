package models

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.Protocol
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

object CountriesService {

    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .protocols(listOf(Protocol.HTTP_1_1)) // Force HTTP/1.1
        //.connectionSpecs(listOf(ConnectionSpec.MODERN_TLS, ConnectionSpec.CLEARTEXT))
        .readTimeout(30, TimeUnit.SECONDS) // Adjust as needed
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
    }

    val service:CountriesApi by lazy {
        retrofit.create(CountriesApi::class.java)
    }
}