package edu.sverrebroen.csumb.flightreservation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_reserve_seat.*

class ReserveSeat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_seat)
        var context = this
        var db = DatabaseHandler(context)




        btnFindFlights.setOnClickListener {
            var inputDeparture = txtDeparture.text.toString()
            var inputArrival = txtDeparture.text.toString()
            var inputSeats = numSeats.text.toString()
            var flights = Flights(0, "Test123", inputDeparture, inputArrival, "10:00(AM)", inputSeats.toInt(), 150.00)
            db.insertFlightsDB(flights)


        }

        btnMain.setOnClickListener{
            var data = db.getFlightsDB()
            txtHeader.text = ""
            for(i in 0 ..data.size - 1){
                txtHeader.append(data.get(i).flightNumber + " " + data.get(i).departure + " " + data.get(i).arrival + " " + data.get(i).capacity + "\n")
            }

        }
        btnMain.setOnLongClickListener{
            finish()
            true
        }


    }

    fun flightExists(flightNumber: String, dataBase : DatabaseHandler) : Boolean{
        var data = dataBase.getFlightsDB()
        for(i in 0 ..data.size -1){
            if(data.get(i).flightNumber == flightNumber){
                Toast.makeText(this, "Flight already used", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }
}
