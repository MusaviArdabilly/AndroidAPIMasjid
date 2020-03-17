package com.example.apimasjid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_jadwal.*
import org.json.JSONArray
import org.json.JSONObject

class Jadwal : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jadwal)

        getdariserver()

        btnKembali.setOnClickListener{
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
        }

        btnPerbaruhi.setOnClickListener {
            var data1 = editsubuh.text.toString()
            var data2 = editdhuha.text.toString()
            var data3 = editduhur.text.toString()
            var data4 = editashar.text.toString()
            var data5 = editmaghrib.text.toString()
            var data6 = editisya.text.toString()
            postJadwal(data1, data2, data3, data4, data5, data6)
            startActivity(Intent(this, Jadwal::class.java))
        }
    }

    fun getdariserver(){
        AndroidNetworking.get("http://192.168.1.14/college/mtandroid_masjid/jadwal.php")
            .setTag("asd")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResponse", response.toString())
                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("shubuh"))

                        subuh.setText(jsonObject.optString("shubuh"))
                        dhuha.setText(jsonObject.optString("dhuha"))
                        duhur.setText(jsonObject.optString("dhuhur"))
                        ashar.setText(jsonObject.optString("ashar"))
                        maghrib.setText(jsonObject.optString("maghrib"))
                        isya.setText(jsonObject.optString("isha"))
                    }
                }

                override fun onError(error: ANError) {
                    Log.i("_err", error.toString())
                }
            })
    }

    fun postJadwal(data1: String, data2: String, data3: String, data4: String, data5: String, data6: String) {
        AndroidNetworking.post("http://$url/u_jadwal.php")
            .addBodyParameter("shubuh", data1)
            .addBodyParameter("dhuha", data2)
            .addBodyParameter("dhuhur", data3)
            .addBodyParameter("ashar", data4)
            .addBodyParameter("maghrib", data5)
            .addBodyParameter("isha", data6)

            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {}
                override fun onError(anError: ANError?) {}

            })
    }
}
