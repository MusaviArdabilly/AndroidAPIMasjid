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
import kotlinx.android.synthetic.main.activity_pengumuman.*
import org.json.JSONArray
import org.json.JSONObject

class Pengumuman : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman)

        btnKembali.setOnClickListener{
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
        }

        getPengumuman()

        btnPerbaruhi.setOnClickListener {
            var data1 = editJudul.text.toString()
            var data2 = editIsi.text.toString()
            updatePengumuman(data1, data2)
            val inten = Intent(this, Pengumuman::class.java)
            startActivity(inten)
        }
    }

    fun updatePengumuman(data1: String, data2: String) {
        AndroidNetworking.post("https://$url/u_pengumuman.php")
            .addBodyParameter("judul_pengumuman", data1)
            .addBodyParameter("isi_pengumuman", data2)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {}
                override fun onError(anError: ANError?) {}

            })
    }

    fun getPengumuman() {
        AndroidNetworking.get("https://$url/pengumuman.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResp", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("judul_pengumuman"))
                        Log.e("_kotlinTitle", jsonObject.optString("isi_pengumuman"))

                        judul.setText(jsonObject.optString("judul_pengumuman"))
                        isi.setText(jsonObject.optString("isi_pengumuman"))

                    }
                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
