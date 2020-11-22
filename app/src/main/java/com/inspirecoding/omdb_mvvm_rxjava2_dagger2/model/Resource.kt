package com.inspirecoding.omdb_mvvm_rxjava2_dagger2.model

import com.inspirecoding.omdb_mvvm_rxjava2_dagger2.utils.Status

data class Resource <out T> (val status : Status, val data : T?, val message: String?) {

    companion object {

        fun <T> success(data : T) : Resource<T> = Resource(
            status = Status.SUCCESS,
            data = data,
            message = null
        )
        fun <T> error(message: String, data : T? = null) : Resource<T> = Resource(
            status = Status.ERROR,
            data = data,
            message = message
        )
        fun <T> loading(data : T? = null) : Resource<T> = Resource(
            status = Status.LOADING,
            data = data,
            message = null
        )

    }

}