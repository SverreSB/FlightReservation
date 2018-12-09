package edu.sverrebroen.csumb.flightreservation

import java.text.SimpleDateFormat
import java.util.*

class Flights {


    var id : Int = 0
    var capacity : Int = 0

    var departure : String= ""
    var arrival : String = ""
    var flightNumber : String = ""
    var time : String = ""

    var price : Double = 0.0



    constructor(id : Int, flightNumber: String, departure : String, arrival : String, time: String, capacity : Int, price : Double){
        this.id = id
        this.flightNumber = flightNumber
        this.departure = departure
        this.arrival = arrival
        this.time = time
        this.capacity = capacity
        this.price = price
    }

    constructor()




}