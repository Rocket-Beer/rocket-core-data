package com.rocket.android.core.data.sample.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rocket.android.core.data.permissions.Permissions

class MainViewModelFactory(private val permissions: Permissions) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        MainViewModel(permissions = permissions) as T
}
