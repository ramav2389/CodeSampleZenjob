package com.zenjob.android.browsr.util

/***
 * In this class the implementation of status for api response is handled.
 */

data class Resource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val errorJson: String? = null
) {
    companion object {
        fun <T> success(data: T): Resource<T> =
            Resource(status = Status.SUCCESS, data = data, message = null)

        fun <T> error(
            data: T? = null,
            message: String = "",
            errorJson: String? = null
        ): Resource<T> =
            Resource(status = Status.ERROR, data = data, message = message, errorJson = errorJson)

        fun <T> loading(): Resource<T> =
            Resource(status = Status.LOADING, data = null, message = null)
    }

}