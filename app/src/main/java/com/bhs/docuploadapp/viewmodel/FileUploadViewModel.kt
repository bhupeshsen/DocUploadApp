package com.bhs.docuploadapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.teach.me.api.ApiClient
import com.teach.me.api.Resource
import com.teach.me.model.error.ErrorResponse
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FileUploadViewModel : ViewModel() {

    fun fileUpload(realPath: RequestBody, document : MultipartBody.Part) = liveData(Dispatchers.IO) {

        emit(Resource.loading(null))
        val api = ApiClient.service
        val response = api.fileUpload(realPath, document)

        if (response.isSuccessful)
            if (response.code() == 200)
                emit(Resource.success(response.body()?.data, response.body()?.message))
            else
                emit(Resource.error(response.body()?.data, response.body()?.message))

        if (!response.isSuccessful) {
            val fileUploadErrorResponse =
                Gson().fromJson(response.errorBody()?.charStream(), ErrorResponse::class.java)
            emit(Resource.error(fileUploadErrorResponse?.data, fileUploadErrorResponse.message))
        }
    }


}