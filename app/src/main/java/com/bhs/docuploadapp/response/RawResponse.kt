package com.bhs.docuploadapp.response

import com.google.gson.annotations.SerializedName

data class RawResponse <T>(
    @SerializedName("data")
    val data: T,
    val message: String
)