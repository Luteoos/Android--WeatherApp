package io.github.luteoos.weatherapp.view

import android.Manifest
import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import android.support.v4.content.ContextCompat
import com.luteoos.kotlin.mvvmbaselib.BaseActivityMVVM
import es.dmoral.toasty.Toasty
import io.github.luteoos.weatherapp.R
import io.github.luteoos.weatherapp.viewmodels.WeatherViewModel
import kotlinx.android.synthetic.main.activity_weather_screen.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.support.v4.ctx

class WeatherScreen: BaseActivityMVVM<WeatherViewModel>() {
    private val LOC_PERM_CODE = 2137

    override fun getLayoutID(): Int = R.layout.activity_weather_screen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = getViewModel(this)
        connectToVMMessage()
        button.onClick {
            if(checkLocPermission())
                getLocationWeather()
            else
                requestLocPermission()
        }
        setBindings()
    }

    private fun setBindings(){
        viewModel.weather.observe(this, Observer { weather -> textView.text = weather.toString()})
    }

    override fun onVMMessage(msg: String?) {
        when(msg){
            "ERROR" -> Toasty.error(this, "unknown error").show()
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