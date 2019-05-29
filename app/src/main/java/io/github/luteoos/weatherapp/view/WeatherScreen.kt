package io.github.luteoos.weatherapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import es.dmoral.toasty.Toasty
import io.github.luteoos.weatherapp.R
import io.github.luteoos.weatherapp.network.response.WeatherResponse
import io.github.luteoos.weatherapp.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather_screen.*
import me.anwarshahriar.calligrapher.Calligrapher
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx
import kotlin.math.roundToLong

class WeatherScreen: BaseActivityMVVM<WeatherViewModel>() {
    private val LOC_PERM_CODE = 666

    override fun getLayoutID(): Int = R.layout.activity_weather_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this)
        Calligrapher(this).setFont(this,"fonts/roboto-regular.ttf", true)
        connectToVMMessage()
        btnGetLoc.onClick {
            if(checkLocPermission())
                getLocationWeather()
            else
                requestLocPermission()
        }
        setBindings()
        btnGetLoc.performClick()
    }

    private fun setBindings(){
        viewModel.weather.observe(this, Observer { weather -> textView.text = weather.toString(); setWeather(weather!!)})
    }

    private fun setWeather(weather: WeatherResponse){
        tvCity.text = weather.name ?: (weather.coord?.lon.toString() +", "+ weather.coord?.lat.toString())
        tvTemp.text = getTempText(weather)
        vIcon.background = getIcon(weather)
        tvCloudPercent.text = "${weather.clouds?.all}%"
        tvPressure.text = "${weather.main?.pressure} Hpa"
        tvHumidity.text = "${weather.main?.humidity}%"
        tvWind.text = "${weather.wind?.speed} m/s  ${getWindDirection(weather.wind?.deg)}"
    }

    private fun getTempText(w: WeatherResponse)= "${String.format("%.1f", (w.main?.temp!! - 273.15))}${viewModel.celcius_sign}"

    private fun getWindDirection(deg: Int?) =
        when(deg!!.toFloat()){
            in 337.6..22.5 -> "N"
            in 22.6..67.5 -> "NE"
            in 67.6..112.5 -> "E"
            in 112.6..157.5 -> "SE"
            in 157.6..202.5 -> "S"
            in 202.6..247.5 -> "SW"
            in 247.6..292.5 -> "W"
            in 292.6..337.5 -> "NW"
            else -> ""
        }

    private fun getIcon(weather:WeatherResponse) =
        getDrawable(when(weather.weather?.first()?.icon){
            "01d" -> R.drawable.ic_01d
            "01n" -> R.drawable.ic_01n
            "02d" -> R.drawable.ic_02d
            "02n" -> R.drawable.ic_02n
            "03d","03n" -> R.drawable.ic_03d
            "04d","04n" -> R.drawable.ic_04d
            "09d","09n" -> R.drawable.ic_09d
            "10d","10n" -> R.drawable.ic_10d
            "11d","11n" -> R.drawable.ic_11d
            "13d","13n" -> R.drawable.ic_13d
            "50d","50n" -> R.drawable.ic_50d
            else -> R.drawable.ic_01d
        })

    override fun onVMMessage(msg: Int?){
        super.onVMMessage(msg)
        when(msg){
            viewModel.ERROR -> Toasty.error(this, "unknown error").show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            LOC_PERM_CODE -> if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
                getLocationWeather()
            else
                Toasty.error(this,getString(R.string.denied_permission)).show()
        }
    }

    private fun requestLocPermission(){
        requestPermissions(arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOC_PERM_CODE)
    }

    @SuppressLint("MissingPermission")
    private fun getLocationWeather(){
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        viewModel.getWeather(loc.latitude.toString(), loc.longitude.toString())
    }

    private fun checkLocPermission(): Boolean =
        (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
}