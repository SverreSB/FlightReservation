package edu.sverrebroen.csumb.flightreservation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val flightReservation = arrayListOf<String>()

        btnReserve.setOnClickListener {
            val newReservation = txtDepature.text.toString() +  ";" +txtDestination.text.toString() + ";"
            flightReservation.add(newReservation)
            txtDepature.text.clear()
            txtDestination.text.clear()
            println(flightReservation)



        }
    }
}
