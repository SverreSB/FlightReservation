package edu.sverrebroen.csumb.flightreservation



class Flights{


    var id : Int = 0
    var capacity : Int = 0
    var soldTickets : Int = 0

    var departure : String= ""
    var arrival : String = ""
    var flightNumber : String = ""
    var time : String = ""

    var price : Double = 0.0



    constructor(flightNumber: String, departure : String, arrival : String, time: String, capacity : Int, soldTickets : Int, price : Double){
        this.flightNumber = flightNumber
        this.departure = departure
        this.arrival = arrival
        this.time = time
        this.capacity = capacity
        this.soldTickets = soldTickets
        this.price = price
    }

    constructor()








}