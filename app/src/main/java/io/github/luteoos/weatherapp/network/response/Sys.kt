package io.github.luteoos.weatherapp.network.response

data class Sys(
    var message: Double?,
    var country: String?,
    var sunrise: Int?,
    var sunset: Int?
)