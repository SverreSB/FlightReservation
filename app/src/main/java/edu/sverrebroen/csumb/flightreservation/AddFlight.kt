package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_add_flight.*


class AddFlight : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flight)



        var db = DatabaseHandler(this)
        val intent = Intent(this, MainActivity::class.java)
        btnAddFlight.setOnClickListener {
            val list: MutableList<String> = ArrayList()
            var flightNumb = txtFlightNumb.text.toString()
            var departure = txtDeparture.text.toString()
            var arrival = txtArrival.text.toString()
            var time = txtTime.text.toString()
            var seats = numbSeats.text.toString()
            var price = decPrice.text.toString()
            list.add(flightNumb)
            list.add(arrival)
            list.add(departure)
            list.add(time)
            list.add(seats)
            list.add(price)

            if(!emptyFields(list) && !existingFlight(flightNumb)){
                var builder = AlertDialog.Builder(this)
                builder.setTitle("Do you want to add flight?")
                builder.setMessage("Are you sure you want to add flight: \n" +
                        "Flightnumber: $flightNumb\nDeparture: $departure\n" +
                        "Arrival: $arrival \nTime: $time \n" +
                        "Seats: $seats \nPrice: $$price \n")
                builder.setPositiveButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->
                    var flight = Flights(flightNumb, departure, arrival, time, seats.toInt(), 0, price.toDouble())
                    db.insertFlightsDB(flight)
                    startActivity(intent)
                }
                builder.setNegativeButton("Cancel"){ dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(this, "Flight $flightNumb is not added", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }
                builder.show()

            }
            else{

                val builder = AlertDialog.Builder(this)
                builder.setTitle("The information given is invalid")
                builder.setMessage("All input fields must be filled in and flight numbers can't be reused")
                builder.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int -> startActivity(intent)}
                builder.show()

            }
        }

        btnMain.setOnClickListener {
            finish()
        }
    }

    fun emptyFields(list : MutableList<String>) : Boolean{
        for(i in 0 ..(list.size -1)){
            if(list[i].isEmpty()){
                Toast.makeText(this, "Fields are empty, please fill in all fields", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    fun existingFlight(flightNumber : String) : Boolean{
        var db = DatabaseHandler(this)
        var data = db.getFlightsDB()
        for(i in 0 ..data.size -1){
            if(data.get(i).flightNumber == flightNumber){
                Toast.makeText(this, "Flight already used", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }




}
