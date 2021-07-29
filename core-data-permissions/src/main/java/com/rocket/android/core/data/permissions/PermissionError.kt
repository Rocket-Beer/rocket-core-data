package com.rocket.android.core.data.permissions

sealed class PermissionError {
    data class SinglePermissionDenied(val data: PermissionDenied) : PermissionError()
    data class MultiplePermissionDenied(val data: List<PermissionDenied>) :
        PermissionError()
}
