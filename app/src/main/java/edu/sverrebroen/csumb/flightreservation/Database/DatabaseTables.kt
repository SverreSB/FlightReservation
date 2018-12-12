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
        val CAPACITY = "capacity"
        val DEPARTURE = "departure"
        val ARRIVAL  = "arrival"
        val FLIGHTNUMBER = "flight_number"
        val TIME = "time"
        val PRICE = "price"
    }


}