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
import kotlinx.android.synthetic.main.activity_identitas.*
import org.json.JSONArray
import org.json.JSONObject

class Identitas : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_identitas)

        btnKembali.setOnClickListener{
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
        }

        getAllIdentitas()

        btnPerbaruhi.setOnClickListener {
            var data1 = identitasNama.text.toString()
            var data2 = identitasAlamat.text.toString()
            postUpdateIdentitas(data1,data2)
            val inten = Intent(this, Identitas::class.java)
            startActivity(inten)
        }
    }

    fun postUpdateIdentitas(data1: String, data2: String) {
        AndroidNetworking.post("https://$url/u_identitas.php")
            .addBodyParameter("nama_masjid", data1)
            .addBodyParameter("alamat_masjid", data2)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {}
                override fun onError(anError: ANError?) {}
            })
    }

    fun getAllIdentitas() {
        AndroidNetworking.get("http://$url/identitas.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResp", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject: JSONObject = jsonArray.getJSONObject(i)
                        namamasjid.setText(jsonObject.optString("nama_masjid"))
                        alamatmasjid.setText(jsonObject.optString("alamat_masjid"))
                    }
                    Log.i("asd", "$namamasjid $alamatmasjid")
                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
