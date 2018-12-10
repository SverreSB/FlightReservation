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


var TAG : String = "DB_Log"

val DATABASENAME = "flightreservation_db"
/*val TABLENAME = "users"
val UUID = "uuid"
val USERNAME = "username"
val PASSWORD = "password"*/

class DatabaseHandler(context : Context) : SQLiteOpenHelper(context, DATABASENAME, null, 1){
    var getContext : Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        /*var flight1 = Flights(1, "Otter101", "Monterey", "Los Angeles", "10:00(AM)", 10, 150.00)
        var flight2 = Flights(2, "Otter102", "Los Angeles", "Monterey", "1:00(PM)", 10, 150.00)*/
        /*val createUserTable = "CREATE TABLE " + TABLENAME + " (" +
                UUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " VARCHAR(256)," +
                PASSWORD + " VARCHAR(256))"*/
        val createUserTable = createUserTable()
        val createFlightTable = createFlightTable()





        db?.execSQL(createUserTable)
        db?.execSQL(createFlightTable)

        /*insertFlightsDB(flight1)
        insertFlightsDB(flight2)*/
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        var sqlUsers = "DROP TABLE IF EXISTS users"
        var sqlFlights = "DROP TABLE IF EXISTS flights"

        db?.execSQL(sqlUsers)
        db?.execSQL(sqlFlights)

        onCreate(db)

    }

    /*fun insertData(user : User){
        val db = this.writableDatabase

        val cv = ContentValues()
        /*cv.put(USERNAME, user.username)
        cv.put(PASSWORD, user.password)*/
        cv.put("username", user.username)
        cv.put("password", user.password)
        var result = db.insert("users", null, cv)
        if(result == -1.toLong()){
            Toast.makeText(getContext, "Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(getContext, "Success", Toast.LENGTH_SHORT).show()
        }
        db.close()

    }*/


    /*fun readData() : MutableList<User>{
        var list : MutableList<User> = ArrayList()
        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLENAME"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()){
            do{
                var user = User()
                user.id = result.getString(result.getColumnIndex(UUID)).toInt()
                user.username = result.getString(result.getColumnIndex(USERNAME))
                user.password = result.getString(result.getColumnIndex(PASSWORD))
                list.add(user)
            }while(result.moveToNext())
        }

        result.close()
        db.close()
        return list
    }*/

    fun insertUserDB(user : User){
        try {
            val db = this.writableDatabase
            val cv = ContentValues()
            cv.put("username", user.username)
            cv.put("password", user.password)
            var result = db.insert("users", null, cv)
            if(result == -1.toLong()){
                Toast.makeText(getContext, "Failed", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(getContext, "Success", Toast.LENGTH_SHORT).show()
            }

            db.close()

        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Did not insert users in insertUserDB()")
        }
    }

    fun getUserDB() : MutableList<User>{
        var list: MutableList<User> = ArrayList()
        try {
            val db = this.readableDatabase
            val query = "SELECT * FROM users"
            val result = db.rawQuery(query, null)
            if (result.moveToFirst()) {
                do {
                    var user = User()
                    user.id = result.getString(result.getColumnIndex("uuid")).toInt()
                    user.username = result.getString(result.getColumnIndex("username"))
                    user.password = result.getString(result.getColumnIndex("password"))
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

    fun insertFlightsDB(flights : Flights){
        try {
            val db = this.writableDatabase

            val cv = ContentValues()
            cv.put("flight_number", flights.flightNumber)
            cv.put("departure", flights.departure)
            cv.put("arrival", flights.arrival)
            cv.put("time", flights.time)
            cv.put("capacity", flights.time)
            cv.put("price", flights.price)
            db.insert("flights", null, cv)

            db.close()
        }catch(sqlExp : SQLiteException){
            Log.d(TAG, "Did not insert flight data in insertFlightDB")
        }



    }

    fun getFlightsDB() : MutableList<Flights>{
        var list : MutableList<Flights> = ArrayList()
        try{
            val db = this.readableDatabase
            val query = "SELECT * FROM flights"
            val result = db.rawQuery(query, null)
            if(result.moveToFirst()){
                do{
                    var flights = Flights()
                    flights.id = result.getString(result.getColumnIndex("uuid")).toInt()
                    flights.flightNumber = result.getString(result.getColumnIndex("flight_number"))
                    flights.departure = result.getString(result.getColumnIndex("departure"))
                    flights.arrival = result.getString(result.getColumnIndex("arrival"))
                    flights.time = result.getString(result.getColumnIndex("time"))
                    flights.capacity = result.getString(result.getColumnIndex("capacity")).toInt()
                    flights.price = result.getString(result.getColumnIndex("price")).toDouble()
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

    fun createFlightTable() : String{
        /*val TABLE = "flights"
        val UID = "uuid"
        val CAPACITY = "capacity"
        val DEPARTURE = "departure"
        val ARRIVAL  = "arrival"
        val FLIGHTNUMBER = "flightnumber"
        val TIME = "time"
        val PRICE = "price"*/

        return "CREATE TABLE flights (uuid INTEGER PRIMARY KEY, " +
                "flight_number VARCHAR(8), departure VARCHAR(32), " +
                "arrival VARCHAR(32), time VARCHAR(9), " +
                "capacity INTEGER, price DECIMAL);"
    }

    fun createUserTable() : String{
        return "CREATE TABLE users (uuid INTEGER PRIMARY KEY," +
                "username VARCHAR(256), password VARCHAR(256));"


    }

}

