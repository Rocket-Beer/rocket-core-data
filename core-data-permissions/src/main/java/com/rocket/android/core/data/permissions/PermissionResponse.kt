package com.rocket.android.core.data.permissions

import com.karumi.dexter.listener.PermissionDeniedResponse

sealed class PermissionResponse {
    object PermissionGranted : PermissionResponse()
    data class SinglePermissionDenied(val data: PermissionDeniedResponse) : PermissionResponse()
    data class MultiplePermissionDenied(val data: List<PermissionDeniedResponse>) :
        PermissionResponse()
}
