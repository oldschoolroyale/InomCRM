package com.kaisho.inomcrm.app.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.kaisho.inomcrm.R

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        setupActionBarWithNavController(findNavController(R.id.regNavHost))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.regNavHost)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}