package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_manage_system.*
import java.util.*


class ManageSystem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_system)

        btnLogin.setOnClickListener {
            var inputUN = txtUsername.text.toString()
            var inputPW = txtPassword.text.toString()
            if(inputUN.length < 1 || inputPW.length < 1){
                Toast.makeText(this, "Empty field(s): Please fill in both username and password in the input fields", Toast.LENGTH_LONG).show()
            }
            if(validate(inputUN, inputPW)){
                val db = DatabaseHandler(this)
                val list = db.getLogsDB()
                Collections.reverse(list)
                var message = ""

                if(list.size == 0){
                    message = "No available logs"
                }

                else{
                    for(log in list){
                        message += "Log : #${log.uuid}\n" +
                                "Username: ${log.username}\n" +
                                "Logtype: ${log.logType}\n" +
                                "Message :\n${log.message}\n" +
                                "\n \n \n"
                    }
                }

                val intent = Intent(this, AddFlight::class.java)
                val logBuilder = AlertDialog.Builder(this)
                val flightBuilder = AlertDialog.Builder(this)

                //AlertDialog for showing all logs.
                logBuilder.setTitle("Log")

                logBuilder.setMessage(message)

                logBuilder.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->flightBuilder.show()}
                logBuilder.show()

                //AlertDialog for passing user to AddFlight or to MainActivity
                flightBuilder.setTitle("Add new Flight")
                flightBuilder.setMessage("Do you want to add a new flight?")
                flightBuilder.setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->startActivity(intent) }
                flightBuilder.setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int -> finish()}
            }

        }





        btnMain.setOnClickListener{
            finish()
        }
    }

    //Validate if username and password for login to manage system is correct
    //Username and password must be "admin2"
    fun validate(username: String, password: String) : Boolean{
        if(username == "admin2" && password == "admin2") {
            return true
        }
        return false
    }
}
