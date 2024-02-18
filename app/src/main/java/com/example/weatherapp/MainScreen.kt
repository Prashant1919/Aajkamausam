package com.example.weatherapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.ViewCompat
import com.example.weatherapp.databinding.ActivityMainScreenBinding
import com.example.weatherapp.databinding.ActivitySplashscreenBinding
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//23648509598542aa62b3a873773ae01b Weather API
class MainScreen : AppCompatActivity() {
    private lateinit var binding: ActivityMainScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainScreenBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main_screen)
        setContentView(binding.root)
        fetchweatherdata("Pune")
        serachcity()


    }

    private fun serachcity() {

        binding.searchview.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchweatherdata(query)
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false

            }

        })

    }

    private fun fetchweatherdata(cityname:String) {
        val retrofit =Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(APIinterface::class.java)
        val response=retrofit.getweatherdata(cityname,"23648509598542aa62b3a873773ae01b","metric")
        response.enqueue(object: Call<Weatherapp>, Callback<Weatherapp> {
            override fun onResponse(call: Call<Weatherapp>, response: Response<Weatherapp>) {
                val responseBody=response.body()
                if(response.isSuccessful && response !=null){
                    val temperture= responseBody?.main?.temp.toString()
                    val humidity=responseBody?.main?.humidity
                    val Windspeed=responseBody?.wind?.speed
                    val sunset=responseBody?.sys?.sunset?.toLong()
                    val sunrise=responseBody?.sys?.sunrise?.toLong()
                    val sealevel=responseBody?.main?.sea_level
                    val condition=responseBody?.weather?.firstOrNull()?.main?:"unknown"
                    val max=responseBody?.main?.temp_max
                    val min=responseBody?.main?.temp_min



                    binding.temperture.text="$temperture °C"
                    binding.condition.text=condition
                    binding.humidityvalue.text="$humidity %"
                    binding.max.text="Max $max °C"
                    binding.min.text="Min $min °C"
                    binding.weather.text=condition
                    binding.wind.text="$Windspeed m/s"
                    binding.sunrise.text="${sunrise?.let { timedate(it) }}"
                    binding.sunset.text="${sunset?.let { timedate(it) }}"
                    binding.cityname.text="$cityname"
                    binding.sea.text="$sealevel hPa"
                    binding.date.text=date()
                    binding.day.text=dayname(System.currentTimeMillis())
                    changeweather(condition)









                }
            }

            override fun `onFailure`(call: Call<Weatherapp>, t: Throwable) {


            }

            override fun clone(): Call<Weatherapp> {
                TODO("Not yet implemented")
            }

            override fun execute(): Response<Weatherapp> {
                TODO("Not yet implemented")
            }

            override fun isExecuted(): Boolean {
                TODO("Not yet implemented")
            }

            override fun cancel() {
                TODO("Not yet implemented")
            }

            override fun isCanceled(): Boolean {
                TODO("Not yet implemented")
            }

            override fun request(): Request {
                TODO("Not yet implemented")
            }

            override fun timeout(): Timeout {
                TODO("Not yet implemented")
                Toast.makeText(applicationContext,"Please enter correct city name",Toast.LENGTH_LONG).show()

            }

            override fun enqueue(callback: Callback<Weatherapp>) {
                TODO("Not yet implemented")
                Toast.makeText(applicationContext,"Please enter correct city name",Toast.LENGTH_LONG).show()
            }


        })
    }

    private fun changeweather(condition:String) {
        when(condition){
            "clear Sky","Sunny","Clear"->{
                binding.root.setBackgroundResource(R.drawable.clearsky)
                binding.lottieicon.setAnimation(R.raw.animation2)
            }
            "partly Clouds","Clouds","Overcast","Mist","Foggy","Smoke"->{
                binding.root.setBackgroundResource(R.drawable.cloudweather)
                binding.lottieicon.setAnimation(R.raw.animation3)

            }
            "Light rain","Drizzle","Moderate Rain","Showers","Heavy Rain"->{
                binding.root.setBackgroundResource(R.drawable.rainy)
                binding.lottieicon.setAnimation(R.raw.animation1)

            }
            "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.root.setBackgroundResource(R.drawable.snow)
                binding.lottieicon.setAnimation(R.raw.animation3)

            }
            else->{
                binding.root.setBackgroundResource(R.drawable.clearsky)
                binding.lottieicon.setAnimation(R.raw.animation2)
            }
        }
        binding.lottieicon.playAnimation()
    }

    fun timedate(timestamp: Long):String{
        val sdf=SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp*1000)))
    }

    fun date():String{
        val sdf=SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return sdf.format((Date()))
    }
    fun dayname(timestamp: Long):String{
        val sdf=SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}