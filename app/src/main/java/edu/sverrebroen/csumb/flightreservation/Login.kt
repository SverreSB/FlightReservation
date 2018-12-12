package edu.sverrebroen.csumb.flightreservation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var intentMain = Intent(this, MainActivity::class.java)

        var flightNumber = intent.getStringExtra("flightNumber")
        var seats = intent.getIntExtra("seats", 0)

        btnLogin.setOnClickListener {
            var username = txtUsername.text.toString()
            var password = txtPassword.text.toString()

            if(validated(username, password)){
                Toast.makeText(this, "Will add alertdialog with flight information here to show flight information", Toast.LENGTH_LONG).show()
            }
        }

        btnMain.setOnClickListener {
            startActivity(intentMain)
        }

    }
    
    fun validated (username : String, password: String) : Boolean{
        var db = DatabaseHandler(this)
        var userList = db.getUserDB()
        for(user in userList){
            if(user.username == username && user.password == password){
                return true
            }
        }
        return false

    }
}
