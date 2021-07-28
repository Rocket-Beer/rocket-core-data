package com.rocket.android.core.data.permissions.di

import android.content.Context
import com.karumi.dexter.Dexter
import com.rocket.android.core.data.permissions.Permissions

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
