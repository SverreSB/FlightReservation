package edu.sverrebroen.csumb.flightreservation.Database

class SqlStatements {

    //Statement for creating a flight table using sql
    fun createFlightTable() : String{
        val Cols = DatabaseTables.FlightCols()

        var sqlFlight = "CREATE TABLE " + Cols.TABLENAME + " (" + Cols.UUID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Cols.FLIGHTNUMBER + " VARCHAR(8), " + Cols.DEPARTURE + " VARCHAR(32), " +
                        Cols.ARRIVAL + " VARCHAR(32), " + Cols.TIME + " VARCHAR(9), " +
                        Cols.CAPACITY + " INTEGER, " + Cols.PRICE + " DECIMAL);"

        return sqlFlight
    }

    //Statement for creating a user table using sql
    fun createUserTable() : String{

        val Cols = DatabaseTables.UserCols()

        var sqlUser = "CREATE TABLE " + Cols.TABLENAME + " (" + Cols.UUID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Cols.USERNAME + " VARCHAR(256), " + Cols.PASSWORD + " VARCHAR(256));"
        return sqlUser
    }

    //Function for sql insert into.
    // Returns a list with 4 insert into sentences for user table that shall be initialized every time the database is created
    fun initializeUsers() : MutableList<String>{

        val Cols = DatabaseTables.UserCols()

        var list : MutableList<String> = ArrayList()

        val user1 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.USERNAME + ", " + Cols.PASSWORD + ") VALUES ('alice5', 'csumb100');"
        val user2 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.USERNAME + ", " + Cols.PASSWORD + ") VALUES ('brian77', '123ABC');"
        val user3 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.USERNAME + ", " + Cols.PASSWORD + ") VALUES ('Chris21', 'CHRIS21');"
        val admin = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.USERNAME + ", " + Cols.PASSWORD + ") VALUES ('admin2', 'admin2');"

        list.add(user1)
        list.add(user2)
        list.add(user3)
        list.add(admin)

        return list
    }

    //Function for sql insert into.
    // Returns a list with 5 insert into sentences for flight table that shall be initialized every time the database is created
    fun initializeFlights() : MutableList<String>{

        val Cols = DatabaseTables.FlightCols()

        var list : MutableList<String> = ArrayList()

        val flight1 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.FLIGHTNUMBER + ", " + Cols.DEPARTURE + ", " +
                Cols.ARRIVAL + ", " + Cols.TIME + ", " + Cols.CAPACITY + ", " + Cols.PRICE +
                ") VALUES ('Otter101', 'Monterey', 'Los Angeles', '10:00(AM)', 10, 150.00);"

        var flight2 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.FLIGHTNUMBER + ", " + Cols.DEPARTURE + ", " +
                Cols.ARRIVAL + ", " + Cols.TIME + ", " + Cols.CAPACITY + ", " + Cols.PRICE +
                ") VALUES ('Otter102', 'Los Angeles', 'Monterey', '01:00(PM)', 10, 150.00);"

        var flight3 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.FLIGHTNUMBER + ", " + Cols.DEPARTURE + ", " +
                Cols.ARRIVAL + ", " + Cols.TIME + ", " + Cols.CAPACITY + ", " + Cols.PRICE +
                ") VALUES ('Otter201', 'Monterey', 'Seattle', '11:00(AM)', 5, 200.50);"

        var flight4 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.FLIGHTNUMBER + ", " + Cols.DEPARTURE + ", " +
                Cols.ARRIVAL + ", " + Cols.TIME + ", " + Cols.CAPACITY + ", " + Cols.PRICE +
                ") VALUES ('Otter205', 'Monterey', 'Seattle', '03:00(PM)', 15, 150.00);"

        var flight5 = "INSERT INTO " + Cols.TABLENAME + " (" + Cols.FLIGHTNUMBER + ", " + Cols.DEPARTURE + ", " +
                Cols.ARRIVAL + ", " + Cols.TIME + ", " + Cols.CAPACITY + ", " + Cols.PRICE +
                ") VALUES ('Otter202', 'Seattle', 'Monterey', '2:00(PM)', 5, 200.50);"


        list.add(flight1)
        list.add(flight2)
        list.add(flight3)
        list.add(flight4)
        list.add(flight5)

        return list
    }
}