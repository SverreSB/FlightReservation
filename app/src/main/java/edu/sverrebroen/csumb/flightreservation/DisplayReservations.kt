package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.support.v7.app.AlertDialog
import android.telephony.RadioAccessSpecifier
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_display_reservations.*

class DisplayReservations : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_reservations)

        var intentMain = Intent(this, MainActivity::class.java)

        var username = intent.getStringExtra("username")
        val linearLayout = findViewById(R.id.linearLayout) as LinearLayout


        var foundReservations = findReservations(username)
        if(foundReservations.isEmpty()){
            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("No reservations")
            dialog.setMessage("No reservations on this username. Return to main menu by pressing 'Confirm'")
            dialog.setPositiveButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->
                startActivity(intentMain)
            }
            dialog.show()
        }

        //var foundFlights = findFlights(foundReservations)



        val radioGroup = RadioGroup(this)
        var uuidList : MutableList<Int> = mutableListOf()
        var ticketList : MutableList<Int> = mutableListOf()
        var flightList : MutableList<Flights> = mutableListOf()

        var index = 0


        for(reservations in foundReservations){
            var flight = getFlightInformation(reservations.flightNumber)
            val radioButton = RadioButton(this)
            radioButton.text = "Reservation number: ${reservations.uuid} \n" +
                            "Flight number: ${reservations.flightNumber} \n" +
                            "To: ${flight.departure} From: ${flight.arrival}\n" +
                            "Tickets: ${reservations.tickets} Price: ${flight.price * reservations.tickets}"
            uuidList.add(index, reservations.uuid)
            ticketList.add(index, reservations.tickets)
            flightList.add(index, flight)
            radioGroup.addView(radioButton)
            index++
        }

        linearLayout.addView(radioGroup)

        radioGroup.setOnCheckedChangeListener{ radioGroup: RadioGroup, i: Int ->
            btnCancel.setOnClickListener {
                val radioButton = findViewById<RadioButton>(i)

                //cancelReservation(uuidList[i-1], ticketList[i-1], flightList[i-1])
                var dialog = AlertDialog.Builder(this)
                dialog.setTitle("Cancel Reservation")
                dialog.setMessage("Are you sure you want to cancel reservation? \n" +
                        "Click 'Confirm' to cancel, click 'No' to be sent back to main menu")
                dialog.setPositiveButton("Confirm"){ dialogInterface: DialogInterface, k : Int ->
                    var db = DatabaseHandler(this)
                    db.cancelReservation(uuidList[i-1], ticketList[i-1], flightList[i-1])
                    var log = Logs(username, "Cancel Reservation", "Reservation number: ${uuidList[i-1]}\n" +
                            "Flight: ${flightList[i-1].flightNumber} \n" +
                            "To: ${flightList[i-1].departure} From: ${flightList[i-1].arrival}\n" +
                            "Departure time: ${flightList[i-1].time} \nTickets: ${ticketList[i-1]}\n")
                    db.insertLog(log)
                    Toast.makeText(this, "Reservation for flight ${flightList[i-1].flightNumber} is canceled", Toast.LENGTH_SHORT).show()
                    startActivity(intentMain)

                }
                dialog.setNegativeButton("No"){ dialogInterface : DialogInterface, j: Int ->
                    val returnDialog = AlertDialog.Builder(this)
                    returnDialog.setTitle("Flight cancellation failed")
                    returnDialog.setMessage("Click 'Confirm' to be sent back to main menu")
                    returnDialog.setPositiveButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->
                        startActivity(intentMain)
                    }
                    returnDialog.show()
                }
                dialog.show()


            }

        }

        btnMain.setOnClickListener {
            startActivity(intentMain)
        }
    }


    fun findReservations(username : String) : MutableList<Reservations>{

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
    }

    fun getFlightInformation(flightNumber: String) : Flights{
        var flight = Flights()
        var db = DatabaseHandler(this)
        var flightList = db.getFlightsDB()

        for(flights in flightList){
            if(flights.flightNumber == flightNumber){
                flight = flights
                /*flight.id = flights.id
                flight.flightNumber = flights.flightNumber
                flight.departure = flights.departure
                flight.arrival = flights.arrival
                flight.time = flights.time
                flight.capacity = flights.capacity
                flight.soldTickets = flights.soldTickets
                flight.price = flights.price*/
                break
            }
        }

        return flight
    }

    fun cancelReservation(reservationNumber: Int, tickets : Int, flight : Flights){
        var dialog = AlertDialog.Builder(this)
        dialog.setTitle("Cancel Reservation")
        dialog.setMessage("Are you sure you want to cancel reservation? \n" +
                "Click 'Confirm' to cancel, click 'No' to be sent back to main menu")
        dialog.setPositiveButton("Confirm"){ _: DialogInterface, _: Int ->
            /*var db = DatabaseHandler(this)
            db.cancelReservation(reservationNumber, tickets, flight)*/

        }
        dialog.setNegativeButton("No"){ _: DialogInterface, _: Int ->
            /*var intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)*/
        }
        dialog.show()
    }
}
