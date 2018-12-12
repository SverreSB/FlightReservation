package edu.sverrebroen.csumb.flightreservation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_flight_options.*

class FlightOptions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flight_options)
        var intentLogin = Intent(this, Login::class.java)
        val intentMain = Intent(this, MainActivity::class.java)

        val linearLayout = findViewById(R.id.linearLayout) as LinearLayout

        val departure = intent.getStringExtra("departure")
        var arrival = intent.getStringExtra("arrival")
        var seats = intent.getIntExtra("seats", 0)


        var foundFlights = findFlights(departure, arrival, seats)

        val radioGroup = RadioGroup(this)

        for(flight in foundFlights){
            val radioButton = RadioButton(this)
            radioButton.text = flight.flightNumber + " " + flight.time
            radioGroup.addView(radioButton)
        }

        linearLayout.addView(radioGroup)

        //This is used to get the user flight choice after user has pressed the button
        //Gets the flight number from the radio button and passes that with seats to a login screen
        radioGroup.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            btnConfirm.setOnClickListener {
                val radioButton = findViewById<RadioButton>(i)
                val radioText = radioButton.text.toString()
                val flightNumber = radioText.substring(0, radioText.indexOf(' '))
                intentLogin.putExtra("flightNumber", flightNumber)
                intentLogin.putExtra("seats", seats)
                startActivity(intentLogin)
            }

        }


        btnMain.setOnClickListener {
            startActivity(intentMain)
        }
    }

    fun findFlights(departure : String, arrival : String, seats : Int) : MutableList<Flights>{

        var list : MutableList<Flights> = ArrayList()

        var db = DatabaseHandler(this)
        var flightList = db.getFlightsDB()

        for(i in 0 ..(flightList.size - 1)){
            if(flightList[i].departure == departure && flightList[i].arrival == arrival){
                if(flightList[i].capacity > seats && seats != 0){
                    list.add(flightList[i])
                }
            }
        }

        return list
    }
}


