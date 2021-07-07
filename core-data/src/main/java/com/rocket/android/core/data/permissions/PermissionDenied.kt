package com.rocket.android.core.data.permissions

data class PermissionDenied(
    val isPermanentlyDenied: Boolean,
    val permissionName: String
)