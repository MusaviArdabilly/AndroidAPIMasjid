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
import kotlinx.android.synthetic.main.activity_tagline.*
import kotlinx.android.synthetic.main.activity_tagline.btnKembali
import kotlinx.android.synthetic.main.activity_tagline.btnPerbaruhi
import org.json.JSONArray
import org.json.JSONObject

class Tagline : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tagline)

        btnKembali.setOnClickListener{
            val inten = Intent(this, MainActivity::class.java)
            startActivity(inten)
        }

        getTagline()

        btnPerbaruhi.setOnClickListener {
            var data1 = editTagline.text.toString()
            updateTagline(data1)
            val inten = Intent(this, Tagline::class.java)
            startActivity(inten)
        }
    }

    fun updateTagline(data1: String) {
        AndroidNetworking.post("http://$url/u_tagline.php")
            .addBodyParameter("isi_tagline", data1)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray?) {}
                override fun onError(anError: ANError?) {}

            })
    }

    fun getTagline() {
        AndroidNetworking.get("http://$url/tagline.php")
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject) {
                    Log.e("_kotlinResp", response.toString())

                    val jsonArray = response.getJSONArray("result")
                    for (i in 0 until jsonArray.length()){
                        val jsonObject = jsonArray.getJSONObject(i)
                        Log.e("_kotlinTitle", jsonObject.optString("isi_tagline"))

                        tagline.setText(jsonObject.optString("isi_tagline"))
                    }
                }

                override fun onError(anError: ANError?) {
                    Log.i("_err", anError.toString())
                }
            })
    }
}
