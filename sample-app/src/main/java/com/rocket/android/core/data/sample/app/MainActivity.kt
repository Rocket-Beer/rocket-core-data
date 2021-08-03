package com.rocket.android.core.data.sample.app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.rocket.android.core.data.permissions.di.CoreDataProvider
import com.rocket.android.core.data.sample.app.databinding.ActivityMainBinding

class MainActivity : FragmentActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel> {
        MainViewModelFactory(
            permissions = CoreDataProvider(
                context = this
            ).provideCorePermissions
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.checkPermissions()
    }
}
