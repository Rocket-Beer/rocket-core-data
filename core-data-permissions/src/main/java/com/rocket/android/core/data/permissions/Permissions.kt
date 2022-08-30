package com.rocket.android.core.data.permissions

import android.annotation.SuppressLint
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.rocket.core.domain.functional.Either
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class Permissions(private val dexter: DexterBuilder.Permission) {

    suspend fun checkMultiplePermissions(permissions: List<String>): Either<PermissionResponse.MultiplePermissionDenied, PermissionResponse.PermissionGranted> =
        suspendCoroutine { continuation ->
            dexter.withPermissions(permissions)
                .withListener(object : MultiplePermissionsListener {

                    @SuppressLint("MissingPermission")
                    override fun onPermissionsChecked(permission: MultiplePermissionsReport) {
                        if (permission.areAllPermissionsGranted()) {
                            continuation.resume(Either.Right(PermissionResponse.PermissionGranted))
                        } else {
                            continuation.resume(
                                Either.Left(
                                    PermissionResponse.MultiplePermissionDenied(
                                        data = permission.deniedPermissionResponses
                                    )
                                )
                            )
                        }
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissionRequest: MutableList<PermissionRequest>,
                        permissionToken: PermissionToken?
                    ) {
                        permissionToken?.continuePermissionRequest()
                    }
                }).check()
        }

    suspend fun checkSinglePermission(permission: String): Either<PermissionResponse.SinglePermissionDenied, PermissionResponse.PermissionGranted> =
        suspendCoroutine { continuation ->
            dexter.withPermission(permission)
                .withListener(object : PermissionListener {

                    override fun onPermissionGranted(permission: PermissionGrantedResponse?) {
                        continuation.resume(Either.Right(PermissionResponse.PermissionGranted))
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permissionRequest: PermissionRequest,
                        permissionToken: PermissionToken?
                    ) {
                        permissionToken?.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(error: PermissionDeniedResponse) {
                        continuation.resume(
                            Either.Left(
                                PermissionResponse.SinglePermissionDenied(
                                    data = error
                                )
                            )
                        )
                    }
                }).check()
        }
}
