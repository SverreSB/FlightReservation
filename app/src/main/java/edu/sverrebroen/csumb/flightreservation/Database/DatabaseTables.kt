package edu.sverrebroen.csumb.flightreservation.Database

class DatabaseTables {

    //All columns for user table
    class UserCols {
        val TABLENAME = "users"
        val UUID = "uuid"
        val USERNAME = "username"
        val PASSWORD = "password"
    }

    //All columns for flight table
    class FlightCols {
        val TABLENAME = "flights"
        val UUID = "uuid"
        val FLIGHTNUMBER = "flight_number"
        val DEPARTURE = "departure"
        val ARRIVAL  = "arrival"
        val TIME = "time"
        val CAPACITY = "capacity"
        val SOLDTICKETS = "sold_tickets"
        val PRICE = "price"
    }

    class ReservationCols{
        val TABLENAME = "reservations"
        val UUID = "uuid"
        val FLIGHTNUMBER = "flight_number"
        val USERNAME = "username"
        val TICKETS = "tickets"
    }


}