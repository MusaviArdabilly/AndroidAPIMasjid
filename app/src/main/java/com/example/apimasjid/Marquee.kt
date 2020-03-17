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
import com.example.apimasjid.Marquee
import kotlinx.android.synthetic.main.activity_marquee.*
import org.json.JSONArray
import org.json.JSONObject

class Marquee : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marquee)

        btnKembali.setOnClickListener{
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
        }

        getAllMarquee()

        btnPerbaruhi.setOnClickListener{
            var data1 = editMarquee.text.toString()
            postUpdateMarquee(data1)
            val inten = Intent(this, Marquee::class.java)
            startActivity(inten)
        }
    }

    fun postUpdateMarquee(data1: String) {
        AndroidNetworking.post("http://$url/u_marquee.php")
            .addBodyParameter("isi_marquee", data1)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {}
                override fun onError(anError: ANError?) {}

            })
    }

    fun getAllMarquee() {
        AndroidNetworking.get("http://$url/marquee.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResp", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("isi_marquee"))

                        marquee.setText(jsonObject.optString("isi_marquee"))
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
