package com.rocket.android.core.data.sample.app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rocket.android.core.data.permissions.Permissions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(private val permissions: Permissions) : ViewModel() {

    fun checkPermissions() {
        viewModelScope.launch(Dispatchers.Default) {
            permissions.checkMultiplePermissions(
                permissions = listOf(
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ).fold(
                ifLeft = { error ->
                    Log.d("Rocket-Beer", "Error with multiple permission -- error = $error")
                },
                ifRight = {
                    Log.d("Rocket-Beer", "Success with multiple permission! :D:D")
                }
            )
        }
    }
}
