package edu.sverrebroen.csumb.flightreservation.UserDatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Flights
import edu.sverrebroen.csumb.flightreservation.User


val DATABASENAME = "flightreservation_db"
val TABLENAME = "users"
val UUID = "uuid"
val USERNAME = "username"
val PASSWORD = "password"

class DatabaseHandler(context : Context) : SQLiteOpenHelper(context, DATABASENAME, null, 1){
    var getContext : Context = context

    override fun onCreate(db: SQLiteDatabase?) {
        val createUserTable = "CREATE TABLE " + TABLENAME + " (" +
                UUID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                USERNAME + " VARCHAR(256)," +
                PASSWORD + " VARCHAR(256))"




        db?.execSQL(createUserTable)
        db?.execSQL(createFlightTable())
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }

    fun insertData(user : User){
        val db = this.writableDatabase

        val cv = ContentValues()
        cv.put(USERNAME, user.username)
        cv.put(PASSWORD, user.password)
        var result = db.insert(TABLENAME, null, cv)
        if(result == -1.toLong()){
            Toast.makeText(getContext, "Failed", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(getContext, "Success", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData() : MutableList<User>{
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
    }

    fun getFlights() : MutableList<Flights>{
        var list : MutableList<Flights> = ArrayList()
    }

    fun createFlightTable() : String{
        val TABLE = "flights"
        val UID = "uuid"
        val CAPACITY = "capacity"
        val DEPARTURE = "departure"
        val ARRIVAL  = "arrival"
        val FLIGHTNUMBER = "flightnumber"
        val TIME = "time"
        val PRICE = "price"

        return "CREATE TABLE $TABLE ($UID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$FLIGHTNUMBER VARCHAR(8), $DEPARTURE VARCHAR(32), " +
                "$ARRIVAL VARCHAR(32), $TIME VARCHAR(9), " +
                "$CAPACITY INTEGER, $PRICE REAL"
    }

}

