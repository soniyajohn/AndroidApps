package models

import retrofit2.http.GET

interface CountriesApi {

    @GET("all")
    suspend fun getCountries():List<Country> // suspend function for asynchronous call

}