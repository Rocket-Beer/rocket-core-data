package com.rocket.android.core.data.sample.app

import android.app.Activity
import android.os.Bundle
import android.util.Log
import com.rocket.android.core.data.permissions.Permissions
import com.rocket.android.core.data.permissions.di.CoreDataProvider
import com.rocket.android.core.data.sample.app.databinding.ActivityMainBinding

class MainActivity : Activity() {

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
