package io.github.luteoos.weatherapp.network

import io.github.luteoos.weatherapp.network.response.WeatherResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("weather")
    fun getWeatherFromLocalization(@Query("lat") latitude: String,
                                   @Query("lon") longitude: String,
                                   @Query("apikey") key: String = "53c2e51466a4c7121b7aa9ae9fe6e3ab" ) : Single<Response<WeatherResponse>>
}