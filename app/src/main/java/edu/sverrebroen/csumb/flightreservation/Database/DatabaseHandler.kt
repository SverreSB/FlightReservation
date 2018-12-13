package edu.sverrebroen.csumb.flightreservation.Database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Flights
import edu.sverrebroen.csumb.flightreservation.User
import android.util.Log
import edu.sverrebroen.csumb.flightreservation.Reservations


var TAG : String = "DB_Log"

val DATABASENAME = "flightreservation_db"


class DatabaseHandler(context : Context) : SQLiteOpenHelper(context, DATABASENAME, null, 1){
    var getContext : Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        try{

            var statements = SqlStatements()

            val createUserTable = statements.createUserTable()
            val createFlightTable = statements.createFlightTable()
            val createReservationTable = statements.createReservationTable()

            val users = statements.initializeUsers()
            val flights = statements.initializeFlights()



            db?.execSQL(createUserTable)
            db?.execSQL(createFlightTable)
            db?.execSQL(createReservationTable)

            //For loop that executes insert into sentences for user table
            for(i in 0 ..(users.size - 1)){
                db?.execSQL(users[i])
            }

            //For loop that executes insert into sentences for flight table
            for(i in 0 ..(flights.size - 1)){
                db?.execSQL(flights[i])
            }

        }catch (sql : SQLiteException){
            Log.d(TAG, "Couldn't create database in onCreate in DatabaseHandler")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var sqlUsers = "DROP TABLE IF EXISTS users"
        var sqlFlights = "DROP TABLE IF EXISTS flights"
        var sqlReservations = "DROP TABLE IF EXISTS reservations"

        db?.execSQL(sqlUsers)
        db?.execSQL(sqlFlights)
        db?.execSQL(sqlReservations)

        onCreate(db)

    }

    //Function for inserting user values into database
    fun insertUserDB(user : User){
        try {
            var Cols = DatabaseTables.UserCols()

            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put(Cols.USERNAME, user.username)
            cv.put(Cols.PASSWORD, user.password)
            db.insert(Cols.TABLENAME, null, cv)

            db.close()

        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Did not insert users in insertUserDB()")
        }
    }

    //Function for reading through database and storing the values in a user list. Returns list
    fun getUserDB() : MutableList<User>{
        var list: MutableList<User> = ArrayList()
        try {
            var Cols = DatabaseTables.UserCols()
            val db = this.readableDatabase
            val query = "SELECT * FROM " + Cols.TABLENAME
            val result = db.rawQuery(query, null)
            if (result.moveToFirst()) {
                do {
                    var user = User()
                    user.id = result.getString(result.getColumnIndex(Cols.UUID)).toInt()
                    user.username = result.getString(result.getColumnIndex(Cols.USERNAME))
                    user.password = result.getString(result.getColumnIndex(Cols.PASSWORD))
                    list.add(user)
                } while (result.moveToNext())
            }
            result.close()
            db.close()
        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Did not retrieve users from getUserDB()")
        }


        return list
    }

    //Function for inserting flight values into database
    fun insertFlightsDB(flights : Flights){
        try {
            val db = this.writableDatabase
            val Cols = DatabaseTables.FlightCols()

            val cv = ContentValues()
            cv.put(Cols.FLIGHTNUMBER, flights.flightNumber)
            cv.put(Cols.DEPARTURE, flights.departure)
            cv.put(Cols.ARRIVAL, flights.arrival)
            cv.put(Cols.TIME, flights.time)
            cv.put(Cols.TIME, flights.time)
            cv.put(Cols.SOLDTICKETS, flights.soldTickets)
            cv.put(Cols.PRICE, flights.price)

            db.insert(Cols.TABLENAME, null, cv)

            db.close()

        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Did not insert flight data in insertFlightDB")
        }



    }

    //Function for reading through database and storing the values in a flight list. Returns list
    fun getFlightsDB() : MutableList<Flights>{
        var list : MutableList<Flights> = ArrayList()
        try{
            val Cols = DatabaseTables.FlightCols()
            val db = this.readableDatabase
            val query = "SELECT * FROM " + Cols.TABLENAME
            val result = db.rawQuery(query, null)
            if(result.moveToFirst()){
                do{
                    var flights = Flights()
                    flights.id = result.getString(result.getColumnIndex(Cols.UUID)).toInt()
                    flights.flightNumber = result.getString(result.getColumnIndex(Cols.FLIGHTNUMBER))
                    flights.departure = result.getString(result.getColumnIndex(Cols.DEPARTURE))
                    flights.arrival = result.getString(result.getColumnIndex(Cols.ARRIVAL))
                    flights.time = result.getString(result.getColumnIndex(Cols.TIME))
                    flights.capacity = result.getInt(result.getColumnIndex(Cols.CAPACITY))
                    flights.soldTickets = result.getInt(result.getColumnIndex(Cols.SOLDTICKETS))
                    flights.price = result.getDouble(result.getColumnIndex(Cols.PRICE))
                    list.add(flights)
                }while(result.moveToNext())
            }
            result.close()
            db.close()


        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Did not retrieve flight information for getFlightsDB")
        }

        return list
    }

    fun insertReservations(reservation : Reservations){
        try{
            val db = this.writableDatabase
            val Cols = DatabaseTables.ReservationCols()
            val cv = ContentValues()

            cv.put(Cols.UUID, reservation.uuid)
            cv.put(Cols.FLIGHTNUMBER, reservation.flightNumber)
            cv.put(Cols.USERNAME, reservation.username)
            cv.put(Cols.TICKETS, reservation.tickets)

            db.insert(Cols.TABLENAME, null, cv)

            db.close()

        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Error in insertReservation() in databasehandler")
        }
    }

    fun getReservationDB() : MutableList<Reservations>{
        var list : MutableList<Reservations> = ArrayList()
        try{
            val Cols = DatabaseTables.ReservationCols()
            val db = this.readableDatabase
            val query = "SELECT * FROM " + Cols.TABLENAME
            val result = db.rawQuery(query, null)
            if(result.moveToFirst()){
                do{
                    var reservation = Reservations()
                    reservation.uuid = result.getString(result.getColumnIndex(Cols.UUID)).toInt()
                    reservation.flightNumber = result.getString(result.getColumnIndex(Cols.FLIGHTNUMBER))
                    reservation.username = result.getString(result.getColumnIndex(Cols.USERNAME))
                    reservation.tickets = result.getInt(result.getColumnIndex(Cols.TICKETS))
                    list.add(reservation)
                }while(result.moveToNext())
            }
            result.close()
            db.close()
        }catch(sql : SQLiteException){
            Log.d(TAG, "Error in getReservationDB in DatabaseHandler")
        }

        return list
    }


    fun updateSoldTickets(tickets : Int, flightNumb : String,  flights : Flights) : Boolean{

        try{
            val db = this.writableDatabase
            val Cols = DatabaseTables.FlightCols()
            val query = "SELECT * FROM " + Cols.TABLENAME
            val result = db.rawQuery(query, null)


            var cv = ContentValues()
            cv.put(Cols.FLIGHTNUMBER, flights.flightNumber)
            cv.put(Cols.DEPARTURE, flights.departure)
            cv.put(Cols.ARRIVAL, flights.arrival)
            cv.put(Cols.TIME, flights.time)
            cv.put(Cols.TIME, flights.time)
            cv.put(Cols.SOLDTICKETS, flights.soldTickets + tickets)
            cv.put(Cols.PRICE, flights.price)

            db.update(Cols.TABLENAME, cv, Cols.FLIGHTNUMBER + " = ?", arrayOf(flightNumb))

            result.close()
            db.close()
            return true

        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Could not read data to get amount of tickets sold. Failed in function updateSoldTickets in database handler")
            return false
        }

    }

}

