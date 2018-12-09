package edu.sverrebroen.csumb.flightreservation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.sverrebroen.csumb.flightreservation.UserDatabase.DatabaseHandler
import kotlinx.android.synthetic.main.activity_create_accout.*
import java.util.regex.Pattern


class CreateAccount : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_accout)
        var context = this
        var db = DatabaseHandler(context)
        btnCreateUser.setOnClickListener {
            //add function her to validate username and password. TOAST correctly
            var inputUN = txtUsername.text.toString()
            var inputPW = txtPassword.text.toString()
            //FIX VALIDATION
            /*if(validateUN(inputUN)){
                Toast.makeText(context, "Is valid", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Is invalid", Toast.LENGTH_SHORT).show()
            }*/

            if(!userExist(inputUN, db)){
                var user = User(txtUsername.text.toString(), inputPW)
                db.insertData(user)

            }
        }

        button2.setOnClickListener {
            var data = db.readData()
            textView2.text = ""
            for(i in 0 ..data.size - 1){
                textView2.append(data.get(i).id.toString() + " " + data.get(i).username + data.get(i).password + "\n")
            }

        }

        btnMain.setOnClickListener{
            finish()
        }

    }

    fun userExist(username : String, dataBase : DatabaseHandler) : Boolean{
        var data = dataBase.readData()
        for(i in 0 ..data.size -1){
            if(data.get(i).username == username){
                Toast.makeText(this, "Username already used", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    fun validateUN(username: String) : Boolean{
        var exp = ".*[0-9].*"
        var pattern = Pattern.compile(exp, Pattern.CASE_INSENSITIVE)
        var matcher = pattern.matcher(username)
        if (!matcher.matches()) {
            return false
        }
        return true
    }
}
