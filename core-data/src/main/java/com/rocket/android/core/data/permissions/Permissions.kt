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

class Permissions(private val dexter: DexterBuilder.Permission) {

    fun checkMultiplePermissions(
        permissions: List<String>,
        onError: (PermissionResponse.MultiplePermissionDenied) -> Unit,
        onSuccess: () -> Unit
    ) {
        dexter.withPermissions(permissions)
            .withListener(object : MultiplePermissionsListener {

                @SuppressLint("MissingPermission")
                override fun onPermissionsChecked(permission: MultiplePermissionsReport) {
                    if (permission.areAllPermissionsGranted()) {
                        onSuccess()
                    } else {
                        onError(
                            PermissionResponse.MultiplePermissionDenied(
                                data = permission.deniedPermissionResponses.map {
                                    PermissionDenied(
                                        isPermanentlyDenied = it.isPermanentlyDenied,
                                        permissionName = it.permissionName
                                    )
                                }
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

    fun checkSinglePermission(
        permission: String,
        onError: (PermissionResponse.SinglePermissionDenied) -> Unit,
        onSuccess: () -> Unit
    ) {
        dexter.withPermission(permission)
            .withListener(object : PermissionListener {

                override fun onPermissionGranted(permission: PermissionGrantedResponse?) {
                    onSuccess()
                }

                override fun onPermissionRationaleShouldBeShown(
                    permissionRequest: PermissionRequest,
                    permissionToken: PermissionToken?
                ) {
                    permissionToken?.continuePermissionRequest()
                }

                override fun onPermissionDenied(error: PermissionDeniedResponse) {
                    onError(
                        PermissionResponse.SinglePermissionDenied(
                            data = PermissionDenied(
                                isPermanentlyDenied = error.isPermanentlyDenied,
                                permissionName = error.permissionName
                            )
                        )
                    )
                }

            }).check()
    }

}