package com.teach.me.api

data class Resource<out T>(val status: APIStatus, val data: T?, val msg: String?){

    companion object{
        fun <T> success(data: T?, msg: String?):Resource<T>{
            return Resource(APIStatus.SUCCESS, data, msg)
        }

        fun <T> verify(data: T?, msg: String?):Resource<T>{
            return Resource(APIStatus.VERIFY, data, msg)
        }

        fun <T> error(data: T? = null, msg: String?):Resource<T>{
            return Resource(APIStatus.ERROR, data, msg)
        }

        fun <T> loading(data: T? = null):Resource<T>{
            return Resource(APIStatus.LOADING, data, null)
        }
    }
}
