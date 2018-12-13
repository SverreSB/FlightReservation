package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*
import java.util.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intentMain = Intent(this, MainActivity::class.java)

        var failedLogin = 0

        var flightNumber = intent.getStringExtra("flightNumber")
        var seats = intent.getIntExtra("seats", 0)

        var flight = findFlight(flightNumber)

        var orderCost = flight.price * seats
        btnLogin.setOnClickListener {
            var username = txtUsername.text.toString()
            var password = txtPassword.text.toString()

            if(failedLogin > 0){
                var dialog = AlertDialog.Builder(this)
                dialog.setTitle("Error! Failed to log in")
                dialog.setMessage("You failed the log in too many times. Click confirm to go back to main menu")
                dialog.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->
                    startActivity(intentMain)
                }
                dialog.show()
            }

            if(validated(username, password)){
                val reservationNumber = randomID()
                var dialog = AlertDialog.Builder(this)
                dialog.setTitle("Confirm Order")
                dialog.setMessage("Username: $username \nFlight number: ${flight.flightNumber} \n" +
                        "From: ${flight.departure} To: ${flight.arrival} \n" +
                        "Number of tickets: $seats Amount owed: $$orderCost\n" +
                        "Reservation number: $reservationNumber \n")
                dialog.setPositiveButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->
                    var reservation = Reservations()
                    val db = DatabaseHandler(this)
                    reservation.uuid = reservationNumber
                    reservation.flightNumber = flightNumber
                    reservation.username = username
                    reservation.tickets = seats
                    db.insertReservations(reservation)
                    db.updateSoldTickets(seats, flightNumber, flight)
                    startActivity(intentMain)

                }
                dialog.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->
                    var cancelDialog = AlertDialog.Builder(this)
                    cancelDialog.setTitle("Cancel reservation")
                    cancelDialog.setMessage("Click 'CONFIRM' to cancel reservation, click 'ORDER' to finish flight reservation")
                    cancelDialog.setPositiveButton("CONFIRM"){ dialogInterface: DialogInterface, i: Int ->
                        startActivity(intentMain)
                    }
                    cancelDialog.setNegativeButton("ORDER"){ dialogInterface: DialogInterface, i: Int ->
                        var reservation = Reservations()
                        val db = DatabaseHandler(this)
                        reservation.uuid = reservationNumber
                        reservation.flightNumber = flightNumber
                        reservation.username = username
                        reservation.tickets = seats
                        db.insertReservations(reservation)
                        db.updateSoldTickets(seats, flightNumber, flight)
                    }
                    cancelDialog.show()
                }
                dialog.show()

            }
            else{
                Toast.makeText(this, "Invalid username/password. Please re-enter username and password to log in", Toast.LENGTH_SHORT).show()
                failedLogin ++
            }
        }

        btnMain.setOnClickListener {
            startActivity(intentMain)
        }

        /*btnMain.setOnLongClickListener {
            var db = DatabaseHandler(this)
            var data = db.getReservationDB()
            txtGetLog.text = ""
            for(i in 0 ..data.size - 1){
                txtGetLog.append(data.get(i).uuid.toString() + " " + data.get(i).flightNumber + " " + data.get(i).username + "\n")
            }

            true
        }*/

    }

    //Function for validating that username and password exists in the database
    //If username is admin2, then it will return false right away
    fun validated (username : String, password: String) : Boolean{
        if(username == "admin2"){
            return false
        }

        var db = DatabaseHandler(this)
        var userList = db.getUserDB()
        for(user in userList){
            if(user.username == username && user.password == password){
                return true
            }
        }
        return false

    }

    //Finding flight object based on flight number found in FlightOptions
    fun findFlight(flightNumber : String) : Flights{
        var db = DatabaseHandler(this)
        var list = db.getFlightsDB()
        var flight = Flights()
        for(flights in list){
            if(flights.flightNumber == flightNumber){
                flight = flights
            }
        }
        return flight
    }

    fun randomID() : Int{
        var reservation = Reservations()
        var random = Random()
        var randomID = 0
        while((randomID < 1000 || randomID > 9999) && !reservation.usedReservations.contains(randomID)){
            randomID = random.nextInt(9000) + 1000
        }
        return randomID
    }
}
