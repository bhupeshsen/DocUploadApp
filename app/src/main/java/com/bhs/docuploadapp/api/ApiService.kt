package com.teach.me.api

import com.bhs.docuploadapp.response.FileUploadResponse
import com.bhs.docuploadapp.response.RawResponse

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @Multipart
    @POST("api/v1/common/file-upload") // this file upload api url also add your api url replace this with your url
    suspend fun fileUpload(
        @Part("base_path") realPath: RequestBody,
        @Part file: MultipartBody.Part
    ): Response<RawResponse<FileUploadResponse>>

}
