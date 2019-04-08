package io.github.luteoos.weatherapp.network.response

data class Main(
    var temp: Double?,
    var pressure: Double?,
    var humidity: Int?,
    var temp_min: Double?,
    var temp_max: Double?,
    var sea_level: Double?,
    var grnd_level: Double?
)