package com.rocket.android.core.data.permissions

import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.rocket.android.core.data.mydexter.MyDexterMultiplePermissions
import com.rocket.android.core.data.mydexter.MyDexterResponse
import com.rocket.android.core.data.mydexter.MyDexterSinglePermission
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.fail
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock

class PermissionsTest {

    private val dexter = mock(DexterBuilder.Permission::class.java)
    private val permissions: Permissions =
        Permissions(dexter = dexter)

    @Test
    fun `given denied permission, when check single permission, denied result received`() =
        runBlocking {
            val permissionName = "permission"

            Mockito.`when`(dexter.withPermission(Mockito.any()))
                .thenReturn(
                    MyDexterSinglePermission(
                        dexterResponse = MyDexterResponse.DENIED,
                        permissionName = permissionName
                    )
                )

            val response = permissions.checkSinglePermission(permission = permissionName)

            assert(response.isLeft())

            response.fold(
                ifLeft = { error ->
                    assert(error is PermissionError.SinglePermissionDenied)
                    assertEquals(
                        permissionName,
                        (error as PermissionError.SinglePermissionDenied).error.permissionName
                    )
                },
                ifRight = {
                    fail()
                }
            )
        }

    @Test
    fun `given granted permission, when check single permission, granted result received`() =
        runBlocking {
            val permissionName = "permission"

            Mockito.`when`(dexter.withPermission(Mockito.any()))
                .thenReturn(
                    MyDexterSinglePermission(
                        dexterResponse = MyDexterResponse.GRANTED,
                        permissionName = permissionName
                    )
                )

            val response = permissions.checkSinglePermission(permission = permissionName)

            assert(response.isRight())
        }

    @Test
    fun `given denied permissions, when check multiple permissions, denied result received`() =
        runBlocking {
            val report = mock(MultiplePermissionsReport::class.java)
            val namePermissions = listOf<String>()

            Mockito.`when`(report.deniedPermissionResponses).thenReturn(
                namePermissions.map {
                    PermissionDeniedResponse(
                        PermissionRequest(it), false
                    )
                }
            )
            Mockito.`when`(report.areAllPermissionsGranted()).thenReturn(false)

            Mockito.`when`(dexter.withPermissions(Mockito.anyCollection())).thenReturn(
                MyDexterMultiplePermissions(
                    dexterResponse = MyDexterResponse.DENIED,
                    report = report
                )
            )

            val response = permissions.checkMultiplePermissions(permissions = namePermissions)

            assert(response.isLeft())

            response.fold(
                ifLeft = { error ->
                    assert(error is PermissionError.MultiplePermissionDenied)
                    assertEquals(
                        namePermissions.size,
                        (error as PermissionError.MultiplePermissionDenied).error.size
                    )
                },
                ifRight = {
                    fail()
                }
            )
        }

    @Test
    fun `given granted permissions, when check multiple permissions, granted result received`() =
        runBlocking {
            val report = mock(MultiplePermissionsReport::class.java)

            Mockito.`when`(report.areAllPermissionsGranted()).thenReturn(true)

            Mockito.`when`(dexter.withPermissions(Mockito.anyCollection())).thenReturn(
                MyDexterMultiplePermissions(
                    dexterResponse = MyDexterResponse.GRANTED,
                    report = report
                )
            )

            val response = permissions.checkMultiplePermissions(permissions = listOf())

            assert(response.isRight())
        }
}
