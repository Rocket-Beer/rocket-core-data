package com.rocket.android.core.data.app

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.rocket.android.app.databinding.ActivityMainBinding
import com.rocket.android.core.data.di.CoreDataProvider
import com.rocket.android.core.data.permissions.Permissions

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val permissions: Permissions by lazy {
        CoreDataProvider(context = this).provideCorePermissions
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        permissions.checkMultiplePermissions(
            permissions = listOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            onError = { error ->
                Log.d("Rocket-Beer", "Error with multiple permission -- error = ${error.data}")
            },
            onSuccess = {
                Log.d("Rocket-Beer", "Success with multiple permission! :D:D")
            }
        )
    }
}
