package io.github.luteoos.weatherapp.network

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.GsonBuilder
import com.google.gson.annotations.Expose
import io.github.luteoos.weatherapp.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object RestApi {
   // private val API_KEY = "53c2e51466a4c7121b7aa9ae9fe6e3ab"

    private val httpClient = OkHttpClient.Builder()
    val gson = GsonBuilder()
        .addSerializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                val expose = fieldAttributes.getAnnotation(Expose::class.java)
                return expose != null && !expose.serialize
            }

            override fun shouldSkipClass(aClass: Class<*>): Boolean = false
        })
        .addDeserializationExclusionStrategy(object : ExclusionStrategy {
            override fun shouldSkipField(fieldAttributes: FieldAttributes): Boolean {
                val expose = fieldAttributes.getAnnotation(Expose::class.java)
                return expose != null && !expose.deserialize
            }

            override fun shouldSkipClass(aClass: Class<*>): Boolean = false
        })
        .create()

    private val builder = Retrofit.Builder()
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(BuildConfig.BASE_URL)

    fun <S> createService(serviceClass: Class<S>): S {
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Content-Type", "charset=utf-8; application/x-www-form-urlencoded")
                //.header("Authorization", API_KEY)
                .header("Accept", "application/json")
                .method(original.method(), original.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        val client = httpClient.build()
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }

    fun <S> createService(serviceClass: Class<S>, accessToken: String): S{
        httpClient.addInterceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("Accept", "application/json")
                .header("Content-Type","application/json")
                .header("Authorization", "Bearer " +  accessToken)
                .method(original.method(), original.body())
            val request = requestBuilder.build()
            chain.proceed(request)
        }
        val client = httpClient.build()
        val retrofit = builder.client(client).build()
        return retrofit.create(serviceClass)
    }
}