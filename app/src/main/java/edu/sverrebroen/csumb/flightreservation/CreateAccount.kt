package edu.sverrebroen.csumb.flightreservation

import android.content.DialogInterface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.Database.DatabaseHandler
import kotlinx.android.synthetic.main.activity_create_accout.*
import java.util.regex.Pattern


class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_accout)
        var context = this
        var db = DatabaseHandler(context)
        var timesFailed = 0
        btnCreateUser.setOnClickListener {
            //add function her to validate username and password. TOAST correctly
            var inputUN = txtUsername.text.toString()
            var inputPW = txtPassword.text.toString()

            if(validateUN(inputUN) && validatePW(inputPW)){
                if(!userExist(inputUN, db)){
                    var user = User(txtUsername.text.toString(), inputPW)
                    var log = Logs("$inputUN", "new account", "$inputUN")
                    db.insertUserDB(user)
                    db.insertLog(log)
                    timesFailed = 0
                    Toast.makeText(this, "Success. User: $inputUN created.", Toast.LENGTH_LONG).show()
                    finish()
                }
                else{
                    timesFailed++
                }
            }
            else{
                timesFailed++
            }

            if(timesFailed > 1){
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Error! Can't create user")
                builder.setMessage("You failed making a user to many times. Please return to main menu")
                builder.setNeutralButton("Confirm"){ dialogInterface: DialogInterface, i: Int -> finish()}
                builder.show()
            }
        }


        btnMain.setOnClickListener{
            finish()
        }

    }


    //Function for validating if customer exists in database
    fun userExist(username : String, dataBase : DatabaseHandler) : Boolean{
        var data = dataBase.getUserDB()
        for(i in 0 ..data.size -1){
            if(data.get(i).username == username){
                Toast.makeText(this, "Username already used", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    //Function for validating username. Shall include 3 characters and 1 integer, with minimum length of 4
    fun validateUN(username: String) : Boolean{
        var exp = "(?=.*[a-zA-Z].*[a-zA-Z].*[a-zA-Z].*)(?=.*\\d)[a-zA-Z0-9]{4,}"
        var pattern = Pattern.compile(exp)
        var matcher = pattern.matcher(username)
        if (!matcher.matches()) {
            Toast.makeText(this, "Username must contain: \n 3 Letters(a-z A-Z) \n 1 number(0-9)", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    //Function for validating username. Shall include 3 characters and 1 integer, with minimum length of 4
    fun validatePW(password: String) : Boolean{
        var exp = "(?=.*[a-zA-Z].*[a-zA-Z].*[a-zA-Z].*)(?=.*\\d)[a-zA-Z0-9]{4,}"
        var pattern = Pattern.compile(exp)
        var matcher = pattern.matcher(password)
        if (!matcher.matches()) {
            Toast.makeText(this, "Password must contain: \n 3 Letters(a-z A-Z) \n 1 number(0-9)", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }
}
