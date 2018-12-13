package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_cancel_reservation.*


class CancelReservation : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_reservation)

        val intentReservation = Intent(this, DisplayReservations::class.java)


        var failedLogin = 0
        btnLogin.setOnClickListener {
            var inputUsername = txtUsername.text.toString()
            var inputPassword = txtPassword.text.toString()

            if(failedLogin > 0){
                val dialog = AlertDialog.Builder(this)
                dialog.setTitle("Failed login")
                dialog.setMessage("You have failed to log in too many time. Please confirm and return to main menu")
                dialog.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int ->
                    finish()
                }
                dialog.show()
            }
            if(validated(inputUsername, inputPassword)){
                intentReservation.putExtra("username", inputUsername)
                startActivity(intentReservation)
            }
            else{
                Toast.makeText(this, "Username or password is wrong, please re enter username and password", Toast.LENGTH_SHORT).show()
                failedLogin++
            }
        }

        btnMain.setOnClickListener{
            finish()
        }
    }

    //Function for validating username and password
    fun validated(username : String, password : String) : Boolean{
        if(username == "admin2"){
            return false
        }

        var db = DatabaseHandler(this)
        var list = db.getUserDB()

        for(user in list){
            if(user.username == username && user.password == password){
                return true
            }
        }

        return false
    }
}


