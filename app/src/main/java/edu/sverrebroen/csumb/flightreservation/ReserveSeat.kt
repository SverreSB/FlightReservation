package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_reserve_seat.*

class ReserveSeat : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserve_seat)
        var context = this
        var db = DatabaseHandler(context)

        var intentMain = Intent(this, MainActivity::class.java)
        var intentFlightOptions = Intent(this, FlightOptions::class.java)




        btnFindFlights.setOnClickListener {
            var inputDeparture = txtDeparture.text.toString()
            var inputArrival = txtArrival.text.toString()
            var inputSeats = numSeats.text.toString().toInt()

            if(inputSeats <= 7){

                //var foundFlights = findFlights(inputDeparture, inputArrival, inputSeats)
                if(foundFlights(inputDeparture, inputArrival, inputSeats)){


                    intentFlightOptions.putExtra("departure", inputDeparture)
                    intentFlightOptions.putExtra("arrival", inputArrival)
                    intentFlightOptions.putExtra("seats", inputSeats)

                    startActivity(intentFlightOptions)

                }
                else{
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Error! No flights found")
                    builder.setMessage("No flights found with given information. Click confirm to go back to main menu")
                    builder.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int -> startActivity(intentMain)}
                    builder.show()
                }
            }
            else{
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error! Too many tickets requested")
                builder.setMessage("You have requested too many seats(max 7), please confirm to go back to main menu")
                builder.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int -> startActivity(intentMain)}
                builder.show()
            }





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

    //Function for finding flights and returning found flights in array
    fun findFlights(departure : String, arrival : String, seats : Int) : MutableList<Flights>{

        var list : MutableList<Flights> = ArrayList()
        //var list : Array<Flights> = arrayOf()

        var index : Int = 0
        var db = DatabaseHandler(this)
        var flightList = db.getFlightsDB()

        for(i in 0 ..(flightList.size - 1)){
            if(flightList[i].departure == departure && flightList[i].arrival == arrival){
                if(flightList[i].capacity > seats){
                    list.add(flightList[i])
                }
            }
        }

        return list
    }

    fun foundFlights(departure : String, arrival : String, seats : Int) : Boolean{
        var db = DatabaseHandler(this)
        var flightList = db.getFlightsDB()

        for(i in 0 ..(flightList.size - 1)){
            if(flightList[i].departure == departure && flightList[i].arrival == arrival){
                if(flightList[i].capacity > seats){
                    return true
                }
            }
        }
        return false
    }

}
