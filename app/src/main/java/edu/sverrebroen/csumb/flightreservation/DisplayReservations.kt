package edu.sverrebroen.csumb.flightreservation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.telephony.RadioAccessSpecifier
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_display_reservations.*

class DisplayReservations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_reservations)

        var intentMain = Intent(this, MainActivity::class.java)

        var username = intent.getStringExtra("username")
        /*val linearLayout = findViewById(R.id.linearLayout) as LinearLayout

        //var foundReservations = findReservations(username)
        //var foundFlights = findFlights(foundReservations)

        val radioGroup = RadioGroup(this)
        val radioButton = RadioButton(this)
        radioButton.text = username
        radioGroup.addView(radioButton)

        /*for(reservations in foundReservations){
            //for(flights in foundFlights){
                //if(flights.flightNumber == reservations.flightNumber){
                    val radioButton = RadioButton(this)
                    radioButton.text = "Reservation number: ${reservations.uuid} \n" +
                            "Flight number: ${reservations.flightNumber} \n" +
                            //"To: ${flights.departure} From: ${flights.arrival}\n" +
                            "Tickets: ${reservations.tickets}"
                    radioGroup.addView(radioButton)
                //}
            //}

        }*/

        linearLayout.addView(radioGroup)*/

        btnMain.setOnClickListener {
            startActivity(intentMain)
        }
    }


    /*fun findReservations(username : String) : MutableList<Reservations>{

        val reservationList : MutableList<Reservations> = ArrayList()

        val db = DatabaseHandler(this)
        val list = db.getReservationDB()

        for(reservations in list){
            if(reservations.username == username){
                var reservation = Reservations()
                reservation.uuid = reservations.uuid
                reservation.flightNumber = reservations.flightNumber
                reservation.username = reservations.username
                reservation.tickets = reservations.tickets
                reservationList.add(reservation)
            }
        }

        return reservationList
    }*/

    /*fun findFlights(reservationList : MutableList<Reservations>) : MutableList<Flights>{

        var list : MutableList<Flights> = ArrayList()

        var db = DatabaseHandler(this)
        var flightList = db.getFlightsDB()

        for(flights in flightList){
            for(reservations in reservationList){
                if(reservations.flightNumber == flights.flightNumber){
                    var flight = Flights()
                    flight.id = flights.id
                    flight.flightNumber = flights.flightNumber
                    flight.departure = flights.departure
                    flight.arrival = flights.arrival
                    flight.time = flights.time
                    flight.capacity = flights.capacity
                    flight.soldTickets = flights.soldTickets
                    flight.price = flights.price
                    list.add(flight)

                }
            }
        }

        return list
    }*/
}
