package com.rocket.android.core.data.mydexter

import com.karumi.dexter.DexterBuilder
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequestErrorListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener

class MyDexterMultiplePermissions(
    private val dexterResponse: MyDexterResponse,
    private val report: MultiplePermissionsReport
) :
    DexterBuilder.MultiPermissionListener {

    override fun withListener(p0: MultiplePermissionsListener?): DexterBuilder =
        MyDexterMultipleBuilder(listener = p0)

    inner class MyDexterMultipleBuilder(private val listener: MultiplePermissionsListener?) :
        DexterBuilder {
        override fun onSameThread(): DexterBuilder {
            return this
        }

        override fun withErrorListener(p0: PermissionRequestErrorListener?): DexterBuilder {
            return this
        }

        override fun check() {
            when (dexterResponse) {
                MyDexterResponse.DENIED, MyDexterResponse.GRANTED -> {
                    listener?.onPermissionsChecked(report)
                }
                MyDexterResponse.RATIONALE -> {
                    listener?.onPermissionRationaleShouldBeShown(
                        listOf(),
                        object : PermissionToken {
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

}