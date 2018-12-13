package edu.sverrebroen.csumb.flightreservation



class Reservations {

    var uuid : Int = 0
    var flightNumber : String = ""
    var username : String = ""
    var tickets : Int = 0

    var usedReservations : MutableList<Int> = mutableListOf()


    constructor(uuid : Int, flightNumber : String, username : String, tickets : Int){
        this.uuid = uuid
        this.flightNumber = flightNumber
        this.username = username
        this.tickets = tickets
        usedReservations.add(uuid)
    }

    constructor()


}