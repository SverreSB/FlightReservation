package edu.sverrebroen.csumb.flightreservation

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Logs {


    var uuid : Int = 0
    var username : String = ""
    var logType : String = ""
    var message : String = ""
    //var date : String = ""


    constructor(username : String, logType : String, message : String){
        this.username = username
        this.logType = logType
        this.message = message
    }

    constructor()


}