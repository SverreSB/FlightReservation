package edu.sverrebroen.csumb.flightreservation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnCreateAccount.setOnClickListener{
            val intent = Intent(this, CreateAccount::class.java)
            startActivity(intent)
        }

        btnReserveSeat.setOnClickListener {
            val intent = Intent(this, ReserveSeat::class.java)
            startActivity(intent)
        }

        btnCancelReservation.setOnClickListener {
            val intent = Intent(this, CancelReservation::class.java)
            startActivity(intent)
        }

        btnManageSystem.setOnClickListener {
            val intent = Intent(this, ManageSystem::class.java)
            startActivity(intent)
        }

    }
}
