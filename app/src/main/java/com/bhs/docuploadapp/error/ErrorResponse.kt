package com.teach.me.model.error

import com.google.gson.annotations.SerializedName

data class ErrorResponse<T> (
    @SerializedName("data")
    val data: T,
    val message: String
)