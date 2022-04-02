package com.bhs.docuploadapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bhs.docuploadapp.databinding.ActivityMainBinding
import com.bhs.docuploadapp.interfaces.MediaPickOptionListener
import com.bhs.docuploadapp.response.FileUploadResponse
import com.bhs.docuploadapp.util.AppUtils
import com.bhs.docuploadapp.util.FileUtil
import com.bhs.docuploadapp.util.PdfOrDocFileUtils
import com.bhs.docuploadapp.util.PermissionUtils
import com.bhs.docuploadapp.viewmodel.FileUploadViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.teach.me.api.APIStatus
import com.teach.me.baseclass.BaseActivity
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.util.*

class MainActivity : BaseActivity(), MediaPickOptionListener {
    private lateinit var binding: ActivityMainBinding
    private val fileUploadViewModel = FileUploadViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.pdfTv.setOnClickListener {
            getMediaOptionDialog(
                this, false,
                getString(R.string.image_screenshot), getString(R.string.pdf_doc), MyCourseActivity@ this
            )
        }
    }





    fun getMediaOptionDialog(context: Context, shouldShowRemove: Boolean,
                             cameraText: String, galleryText: String, mediaListener: MediaPickOptionListener
    ){
        val dialog = BottomSheetDialog(context, R.style.BottomSheetDialog)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.edit_image_bottom_sheet_dialog)
        val cancelTv = dialog.findViewById<View>(R.id.cancel_tv) as TextView
        val galleryTv = dialog.findViewById<View>(R.id.choose_tv) as TextView
        val cameraTv = dialog.findViewById<View>(R.id.take_tv) as TextView
        val removeTv = dialog.findViewById<View>(R.id.remove_tv) as TextView

        cameraTv.text = cameraText
        galleryTv.text = galleryText


        if (shouldShowRemove)
            removeTv.visibility = View.VISIBLE
        else
            removeTv.visibility = View.GONE

//    if (shouldShowCamera)
//        cameraTv.visibility = View.VISIBLE
//    else
//        cameraTv.visibility = View.GONE

        cancelTv.setOnClickListener {
            dialog.dismiss()
        }

        cameraTv.setOnClickListener {
            dialog.dismiss()
            mediaListener.onCamera()
        }
        galleryTv.setOnClickListener {
            dialog.dismiss()
            mediaListener.onGallery()
        }

        removeTv.setOnClickListener {
            dialog.dismiss()
            mediaListener.onRemove()
        }
        dialog.show()
    }




    private var activityResultLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val data = result.data
                val clipData = Objects.requireNonNull(data)?.clipData

                if (clipData != null) {
                    val fileSize: Long =
                        PdfOrDocFileUtils.getFileSize(this, clipData.getItemAt(0).uri)
                    val fileName = PdfOrDocFileUtils.getCorrectFileName(
                        this,
                        clipData.getItemAt(0).uri
                    )
                    if (fileSizeValidation(fileSize, this))
                        uploadFile(
                            "document",
                            File(
                                FileUtil.getRealPathFromURI(
                                    this,
                                    clipData.getItemAt(0).uri
                                )
                            ),
                            fileName
                        )
                } else {
                    val tempUri = data!!.data
                    val fileSize: Long = PdfOrDocFileUtils.getFileSize(this, tempUri)

                    if (tempUri != null) {
                        if (fileSizeValidation(fileSize, this)) {

                            val fileName =
                                PdfOrDocFileUtils.getCorrectFileName(this, tempUri)
                            val filePath =
                                PdfOrDocFileUtils.getFilePathFromURI(this, tempUri)
                            if (!filePath.isNullOrEmpty())
                                uploadFile("document", File(filePath), fileName)
                        }
                    } else {

                        Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()


                    }
                }

            }
        }

    private fun uploadFile(basePath: String, file: File, fileName: String?) {
        val basePathReq: RequestBody =
            basePath.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val document: RequestBody =
            file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val body = MultipartBody.Part.createFormData("document", fileName ?: file.name, document)

        if (AppUtils.Network.networkValidationWithMsg(this)) {
            upload(basePathReq, body, file, fileName ?: file.name)
        }

    }

    private fun upload(
        basePathReq: RequestBody,
        document: MultipartBody.Part,
        file: File,
        fileName: String
    ) {
        fileUploadViewModel.fileUpload(basePathReq, document).observe(this, { networkResource ->
            when (networkResource.status) {

                APIStatus.LOADING -> {
                    progressDialog(true)
                }
                APIStatus.SUCCESS -> {
                    progressDialog(false)
                    val fileResponse = networkResource.data as FileUploadResponse
                    binding.fileName.text = fileResponse.name
                    Toast.makeText(this, "File successfully uploaded ", Toast.LENGTH_SHORT).show()
                    if (file.exists())
                        file.delete()
                }

                APIStatus.ERROR -> {
                    progressDialog(false)
                    networkResource.msg?.let {
                        Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

                    }
                }

            }
        })
    }

    override fun onRemove() {
    }

    //    here for gallery picker
    override fun onCamera() {
        if (PermissionUtils.showGalleryPermission(this)) {
            activityResultLauncher.launch(AppUtils.IntentUtils.getGalleryIntent())
        }
    }

    //    here for pdf & doc picker
    override fun onGallery() {
        if (PermissionUtils.showDocPermission(this)) {
            activityResultLauncher.launch(AppUtils.IntentUtils.getPDFDOCIntent())
        }
    }

    override fun onCancel() {

    }

    fun fileSizeValidation(fileSize: Long, context: Context): Boolean {
        return if (fileSize <=  10) true
        else {
            Toast.makeText(context, "File size must be less than 10 MB", Toast.LENGTH_SHORT).show()

            false
        }
    }



}