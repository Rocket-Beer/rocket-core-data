package com.rocket.android.core.data.di

import android.content.Context
import com.rocket.android.core.data.permissions.Permissions
import com.karumi.dexter.Dexter

@Suppress("unused")
class CoreDataProvider(private val context: Context) {

    val provideCorePermissions: Permissions by lazy {
        Permissions(
            dexter = Dexter.withContext(
                context
            )
        )
    }

}