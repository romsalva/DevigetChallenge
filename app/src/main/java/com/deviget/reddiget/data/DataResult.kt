package com.deviget.reddiget.data

fun <T, U> DataResult<T>.map(block: (T) -> U): DataResult<U> =
    when (this) {
        is DataResult.Success -> DataResult.Success(block(this.data))
        is DataResult.Failure -> DataResult.Failure(this.throwable)
    }

/**
 * Result from an arbitrary operation that provides data on success.
 */
sealed class DataResult<T> {
    class Success<T>(val data: T) : DataResult<T>()
    class Failure<T>(val throwable: Throwable) : DataResult<T>() {
        val message: String = throwable.message.orEmpty()
    }
}