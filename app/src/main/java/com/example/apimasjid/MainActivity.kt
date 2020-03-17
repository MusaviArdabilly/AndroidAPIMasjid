package com.example.apimasjid

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidnetworking.AndroidNetworking
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

val url = "192.168.1.14/college/mtandroid_masjid"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnJadwal.setOnClickListener{
            val inten = Intent(this, Jadwal::class.java)
            startActivity(inten)
        }

        btnIdentitas.setOnClickListener{
            val inten = Intent(this, Identitas::class.java)
            startActivity(inten)
        }

        btnMarquee.setOnClickListener{
            val inten = Intent(this, Marquee::class.java)
            startActivity(inten)
        }

        btnPengumuman.setOnClickListener{
            val inten = Intent(this, Pengumuman::class.java)
            startActivity(inten)
        }

        btnSlideshow.setOnClickListener{
            val inten = Intent(this, Slideshow::class.java)
            startActivity(inten)
        }

        btnTagline.setOnClickListener{
            val inten = Intent(this, Tagline::class.java)
            startActivity(inten)
        }
    }
}
