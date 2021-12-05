package com.zenjob.android.browsr.base

import android.app.Application
import android.util.Log
import com.zenjob.android.browsr.network.EmptyJsonResponse
import com.zenjob.android.browsr.util.RecyclableCompositeDisposable
import com.zenjob.android.browsr.util.Resource
import kotlinx.coroutines.*
import retrofit2.Response


abstract class BaseCoroutinesViewModel(application: Application) :
    BaseAndroidViewModel(application) {

    private val recyclableCompositeDisposable by lazy { RecyclableCompositeDisposable() }

    /**
     * This is the main scope for all coroutines launched by ViewModel.
     * Since we pass viewModelJob, you can cancel all coroutines
     * launched by uiScope by calling viewModelJob.cancel()
     */

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("ERROR", "on error: $throwable")
    }

    var ioScope = CoroutineScope(Dispatchers.IO + coroutineExceptionHandler)

    var job: CompletableJob = SupervisorJob()

    private fun <T> parseNetworkResult(response: Response<T>): Resource<T> {
        if (response.isSuccessful) {
            val body = response.body()
            if (body != null && body !is EmptyJsonResponse) return Resource.success(body)
        }

        return Resource.error(message = "", errorJson = response.errorBody()?.string())
    }

    private fun getParentJob(): CompletableJob {
        // reset parentJob if canceled
        if (job.isCancelled) {
            job = SupervisorJob()
        }
        return job
    }

    fun <T : Any?> launchNetworkJob(
        networkRequest: suspend () -> Response<T>,
        onResult: suspend (Resource<T>) -> Unit
    ) {
        // launch Network Request with parentJob
        ioScope.launch(getParentJob()) {
            val result = async { parseNetworkResult(networkRequest()) }
            onResult(result.await())
        }
    }

    fun cancelParentJob() {
        // if parentJob is canceled all jobs launched within the parentJob will be canceled
        job.cancel()
    }

    override fun onCleared() {
        cancelParentJob()
        recyclableCompositeDisposable.disposeAndReset()
        super.onCleared()
    }
}