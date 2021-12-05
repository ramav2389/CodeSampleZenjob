package com.zenjob.android.browsr.util

import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class RecyclableCompositeDisposable @Inject constructor() {

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    /**
     * This method has to get called for disposing the  CompositeDisposal
     */
    fun disposeAndReset() {
        compositeDisposable.let {
            compositeDisposable = CompositeDisposable()
            it.dispose()
        }
    }
}