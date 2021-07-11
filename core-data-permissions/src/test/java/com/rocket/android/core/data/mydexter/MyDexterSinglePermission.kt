package com.rocket.android.core.data.mydexter

import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.single.PermissionListener

class MyDexterSinglePermission(
    private val dexterResponse: MyDexterResponse,
    private val permissionName: String
) :
    DexterBuilder.SinglePermissionListener {

    override fun withListener(p0: PermissionListener?): DexterBuilder {
        return MyDexterSingleBuilder(p0)
    }

    inner class MyDexterSingleBuilder(private val listener: PermissionListener?) : DexterBuilder {
        override fun onSameThread(): DexterBuilder {
            return this
        }

        override fun withErrorListener(p0: PermissionRequestErrorListener?): DexterBuilder {
            return this
        }

        override fun check() {
            when (dexterResponse) {
                MyDexterResponse.GRANTED -> listener?.onPermissionGranted(
                    PermissionGrantedResponse(
                        PermissionRequest(permissionName)
                    )
                )
                MyDexterResponse.DENIED -> listener?.onPermissionDenied(
                    PermissionDeniedResponse(
                        PermissionRequest(permissionName),
                        false
                    )
                )
                MyDexterResponse.RATIONALE -> listener?.onPermissionRationaleShouldBeShown(
                    PermissionRequest(permissionName), object : PermissionToken {
                        override fun continuePermissionRequest() {
                            // Nothing to implement.
                        }

                        override fun cancelPermissionRequest() {
                            // Nothing to implement.
                        }
                    })
            }
        }
    }
}
