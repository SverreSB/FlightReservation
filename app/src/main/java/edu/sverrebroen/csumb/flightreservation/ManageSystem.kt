package edu.sverrebroen.csumb.flightreservation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_accout.*

class ManageSystem : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_system)

        btnMain.setOnClickListener{
            finish()
        }
    }
}