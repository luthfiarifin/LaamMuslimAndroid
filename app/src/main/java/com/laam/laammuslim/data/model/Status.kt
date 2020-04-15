package com.laam.laammuslim.data.model

// Resource for status
class Status<T>(val status: StatusType, val data: T?, val message: String?) {

    enum class StatusType {
        ERROR, LOADING, SUCCESS
    }

    companion object {
        fun <T> error(msg: String, data: T?): Status<T> {
            return Status(StatusType.ERROR, data, msg)
        }

        fun <T> loading(): Status<T> {
            return Status(StatusType.LOADING, null, null)
        }

        fun <T> success(data: T?): Status<T> {
            return Status(StatusType.SUCCESS, data, null)
        }
    }
}
