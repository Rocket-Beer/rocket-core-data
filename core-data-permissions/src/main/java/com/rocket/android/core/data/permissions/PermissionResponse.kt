package com.rocket.android.core.data.permissions

sealed class PermissionResponse {
    data class SinglePermissionDenied(val data: PermissionDenied) : PermissionResponse()
    data class MultiplePermissionDenied(val data: List<PermissionDenied>) :
        PermissionResponse()
}
