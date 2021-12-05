package com.zenjob.android.browsr.base

import android.app.Application
import android.os.Looper
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData


abstract class BaseAndroidViewModel(application: Application) : AndroidViewModel(application) {
    fun onManualClear() = onCleared()

    protected fun <T> notifyLiveDataValue(liveData: MutableLiveData<T>, t: T?) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            liveData.setValue(t)
        } else {
            liveData.postValue(t)
        }
    }
}