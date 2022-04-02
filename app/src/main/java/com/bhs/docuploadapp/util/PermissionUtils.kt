package com.bhs.docuploadapp.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bhs.docuploadapp.R
import com.bhs.docuploadapp.util.Constants.CAMERA_PERMISSION_REQUEST_CODE
import com.bhs.docuploadapp.util.Constants.DOC_PERMISSION_REQUEST_CODE
import com.bhs.docuploadapp.util.Constants.GALLERY_PERMISSION_REQUEST_CODE


object PermissionUtils {

    /*CAMERA PERMISSION*/
    fun showCameraPermission(mCtx: Activity): Boolean {
        return if (isCamPermissionGranted(mCtx)) {
            true
        } else {
            val permissionList: Array<String> = if (isWritePermissionGranted(mCtx)) {
                arrayOf(Manifest.permission.CAMERA)
            } else {
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mCtx,
                    Manifest.permission.CAMERA
                ) || ActivityCompat.shouldShowRequestPermissionRationale(
                    mCtx,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                showDialogForPermission(
                    mCtx,
                    mCtx.resources.getString(R.string.cam_permission_message),
                    permissionList,
                    CAMERA_PERMISSION_REQUEST_CODE,
                    false
                )
            } else {
                ActivityCompat.requestPermissions(
                    mCtx,
                    permissionList,
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            }
            false
        }
    }

    private fun isCamPermissionGranted(mCtx: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            mCtx!!,
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    mCtx,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun isWritePermissionGranted(context: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun onRequestCamPermissionResult(requestCode: Int, grantResults: IntArray): Boolean {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE) {
            val grantResultsCount = grantResults.size
            return if (grantResultsCount == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                true
            } else grantResultsCount > 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    /*GALLERY PERMISSION*/
    fun showGalleryPermission(mCtx: Activity): Boolean {
        return if (isStoragePermissionGranted(mCtx)) {
            true
        } else {
            val permissionList = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mCtx,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                showDialogForPermission(
                    mCtx,
                    mCtx.resources.getString(R.string.gallery_permission_message),
                    permissionList,
                    GALLERY_PERMISSION_REQUEST_CODE,
                    false
                )
            } else {
                ActivityCompat.requestPermissions(
                    mCtx,
                    permissionList,
                    GALLERY_PERMISSION_REQUEST_CODE
                )
            }
            false
        }
    }

    private fun isStoragePermissionGranted(mCtx: Context?): Boolean {
        return ContextCompat.checkSelfPermission(
            mCtx!!,
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun onRequestGalleryPermissionResult(requestCode: Int, grantResults: IntArray): Boolean {
        return requestCode == GALLERY_PERMISSION_REQUEST_CODE && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    /*DOC PERMISSION*/
    fun showDocPermission(mCtx: Activity): Boolean {
        return if (isStoragePermissionGranted(mCtx)) {
            true
        } else {
            val permissionList = arrayOf(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE)

            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mCtx,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
            ) {
                showDialogForPermission(
                    mCtx,
                    mCtx.resources.getString(R.string.doc_permission_message),
                    permissionList,
                    DOC_PERMISSION_REQUEST_CODE,
                    false
                )
            } else {
                ActivityCompat.requestPermissions(
                    mCtx,
                    permissionList,
                    DOC_PERMISSION_REQUEST_CODE
                )
            }
            false
        }
    }

    fun onRequestDocPermissionResult(requestCode: Int, grantResults: IntArray): Boolean {
        return requestCode == DOC_PERMISSION_REQUEST_CODE && grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

    /*SHOW RATIONAL DIALOG TO INTIMATE USER*/
    private fun showDialogForPermission(
        mCtx: Activity,
        msg: String,
        permissionList: Array<String>,
        permissionReqCode: Int,
        isForCalPerm: Boolean
    ) {
        val msgWithAppName: String = getMsgWithAppName(mCtx, msg)
        val alertDialogBuilder = AlertDialog.Builder(ContextThemeWrapper(mCtx, R.style.myDialog))
        if (isForCalPerm) {
            alertDialogBuilder.setOnCancelListener { mCtx.finish() }
        }
        alertDialogBuilder.setMessage(msgWithAppName)
        alertDialogBuilder.setPositiveButton(
            "Yes"
        ) { arg0, arg1 ->
            ActivityCompat.requestPermissions(
                mCtx,
                permissionList,
                permissionReqCode
            )
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    private fun getMsgWithAppName(mCtx: Context, msg: String): String {
        return mCtx.resources.getString(R.string.app_name) + " " + msg
    }

}