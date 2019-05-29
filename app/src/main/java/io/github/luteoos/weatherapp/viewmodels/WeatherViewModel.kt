package io.github.luteoos.weatherapp.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.luteoos.kotlin.mvvmbaselib.BaseViewModel
import io.github.luteoos.weatherapp.network.RestApi
import io.github.luteoos.weatherapp.network.WeatherApi
import io.github.luteoos.weatherapp.network.response.WeatherResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class WeatherViewModel: BaseViewModel() {

    val ERROR = 2137
    val celcius_sign = "\u2103"

    val disposable: CompositeDisposable = CompositeDisposable()
    val weather: MutableLiveData<WeatherResponse> = MutableLiveData()

    fun getWeather(lat: String, lon: String){
        val client = RestApi.createService(WeatherApi::class.java)
        disposable.add(client.getWeatherFromLocalization(lat, lon)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({
                if(it.code() == 200)
                    weather.value = it.body()
            },{
            send(ERROR)
        }))
    }
}