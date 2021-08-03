package com.rocket.android.core.data.permissions

import com.rocket.core.domain.error.Failure

sealed class PermissionError<T>(val error: T) : Failure.FeatureFailure(data = error) {
    class SinglePermissionDenied(data: PermissionDenied) :
        PermissionError<PermissionDenied>(error = data)

    class MultiplePermissionDenied(data: List<PermissionDenied>) :
        PermissionError<List<PermissionDenied>>(error = data)
}
