package io.github.luteoos.weatherapp.network.response

data class Weather(
    var id: Int?,
    var main: String?,
    var description: String?,
    var icon: String?
)