package com.example.weatherapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn.setOnClickListener{
            val city = updateid.text.toString()
            val okHttpClient = OkHttpClient()
            val request = Request.Builder()
                .url("https://api.openweathermap.org/data/2.5/weather?q=${city}&units=metric&appid=e9f9c92cf80afcbb8247158c36cc7bfe")
                .build()

            GlobalScope.launch(Dispatchers.Main) {
                val response = withContext(Dispatchers.IO) {
                    okHttpClient.newCall(request).execute().body?.string()
                }
                val obj = JSONObject(response)
                val name = obj.getString("name")
                val base = obj.getString("base")
                val main = obj.getJSONObject("main")
                val sys = obj.getJSONObject("sys")
                val wind = obj.getJSONObject("wind")
                val weather = obj.getJSONArray("weather").getJSONObject(0)
                val temp = main.getString("temp") + "°C"
                val tempMin = "Min Temp:" + main.getString("temp_min") + "°C"
                val tempMax = "Max Temp:" + main.getString("temp_max") + "°C"
                val pressure = main.getString("pressure")
                val humidity = main.getString("humidity")
                val windSpeed = wind.getString("speed")
                val weatherDescription = weather.getString("description")

                temperature.text = temp
                temp_min.text = tempMin
                temp_max.text = tempMax
                pressure1.text = pressure
                humidity1.text = humidity
                wind1.text = windSpeed
                status.text = weatherDescription


            }

        }


    }
}
