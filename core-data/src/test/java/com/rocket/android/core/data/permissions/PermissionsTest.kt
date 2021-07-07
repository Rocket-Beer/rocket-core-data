package com.rocket.android.core.data.permissions

import com.rocket.android.core.data.mydexter.MyDexterMultiplePermissions
import com.rocket.android.core.data.mydexter.MyDexterResponse
import com.rocket.android.core.data.mydexter.MyDexterSinglePermission
import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionRequest
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PermissionsTest {

    private val dexter = mock(DexterBuilder.Permission::class.java)
    private val permissions: Permissions =
        Permissions(dexter = dexter)

    @Test
    fun `given denied permission, when check single permission, denied result received`() {
        val permissionName = "permission"

        Mockito.`when`(dexter.withPermission(Mockito.any()))
            .thenReturn(
                MyDexterSinglePermission(
                    dexterResponse = MyDexterResponse.DENIED,
                    permissionName = permissionName
                )
            )

        permissions.checkSinglePermission(
            permission = permissionName,
            onError = { failure: PermissionResponse.SinglePermissionDenied ->
                assertEquals(permissionName, failure.data.permissionName)
            },
            onSuccess = {
                fail()
            })
    }

    @Test
    fun `given granted permission, when check single permission, granted result received`() {
        val permissionName = "permission"

        Mockito.`when`(dexter.withPermission(Mockito.any()))
            .thenReturn(
                MyDexterSinglePermission(
                    dexterResponse = MyDexterResponse.GRANTED,
                    permissionName = permissionName
                )
            )

        permissions.checkSinglePermission(
            permission = permissionName,
            onError = {
                fail()
            },
            onSuccess = {
                assert(true)
            },
        )
    }

    @Test
    fun `given denied permissions, when check multiple permissions, denied result received`() {
        val report = mock(MultiplePermissionsReport::class.java)
        val namePermissions = listOf<String>()

        Mockito.`when`(report.deniedPermissionResponses).thenReturn(namePermissions.map {
            PermissionDeniedResponse(
                PermissionRequest(it), false
            )
        })
        Mockito.`when`(report.areAllPermissionsGranted()).thenReturn(false)

        Mockito.`when`(dexter.withPermissions(Mockito.anyCollection())).thenReturn(
            MyDexterMultiplePermissions(
                dexterResponse = MyDexterResponse.DENIED,
                report = report
            )
        )

        permissions.checkMultiplePermissions(
            permissions = namePermissions,
            onError = { failure: PermissionResponse.MultiplePermissionDenied ->
                assertEquals(namePermissions.size, failure.data.size)
            },
            onSuccess = {
                fail()
            }
        )
    }

    @Test
    fun `given granted permissions, when check multiple permissions, granted result received`() {
        val report = mock(MultiplePermissionsReport::class.java)

        Mockito.`when`(report.areAllPermissionsGranted()).thenReturn(true)

        Mockito.`when`(dexter.withPermissions(Mockito.anyCollection())).thenReturn(
            MyDexterMultiplePermissions(
                dexterResponse = MyDexterResponse.GRANTED,
                report = report
            )
        )

        permissions.checkMultiplePermissions(
            permissions = listOf(),
            onError = {
                fail()
            },
            onSuccess = {
                assert(true)
            }
        )
    }

}