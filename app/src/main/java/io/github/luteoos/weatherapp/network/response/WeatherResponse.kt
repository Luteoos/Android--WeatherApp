package io.github.luteoos.weatherapp.network.response

data class WeatherResponse(
    var coord: Coord?,
    var weather: List<Weather?>?,
    var base: String?,
    var main: Main?,
    var wind: Wind?,
    var clouds: Clouds?,
    var dt: Int?,
    var sys: Sys?,
    var id: Int?,
    var name: String?,
    var cod: Int?
)