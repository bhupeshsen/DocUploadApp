package com.bhs.docuploadapp.util

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore

import android.widget.Toast
import java.io.File


class AppUtils {

    object Network {
        @Suppress("DEPRECATION")
        private fun getConnectionType(context: Context): Boolean {
            var result = false // Returns connection type. 0: none; 1: mobile data; 2: wifi
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    cm?.run {
                        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                            if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                                    NetworkCapabilities.TRANSPORT_CELLULAR
                                )
                            )
                                result = true
                        }
                    }
                }
                else -> {
                    cm?.run {
                        cm.activeNetworkInfo?.run {
                            if (type == ConnectivityManager.TYPE_WIFI || type == ConnectivityManager.TYPE_MOBILE)
                                result = true

                        }
                    }
                }
            }
            return result
        }

        fun networkValidationWithMsg(context: Context): Boolean {
            if (!getConnectionType(context)) {
                Toast.makeText(context, "SomeThing Went wrong", Toast.LENGTH_SHORT).show()
                return false
            } else return true

        }
    }
    object IntentUtils {
        fun getCameraIntent(): Intent {
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val photo = File(Environment.getExternalStorageDirectory(), "Pic.jpg")
            intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(photo)
            )
            return intent
        }

        fun getPDFDOCIntent(): Intent {
            val intent =
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                } else {
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                }
            //  In this example we will set the type to video
            val mimeTypes = arrayOf(
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",
                "application/msword",
                "application/pdf"
            )

            intent.action = Intent.ACTION_OPEN_DOCUMENT
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            intent.type = "*/*"
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            intent.putExtra("return-data", true)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            return intent
        }

        fun getGalleryIntent(): Intent {
            val intent =
                if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                } else {
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                }
            intent.type = "image/*"
            //  intent.action = Intent.ACTION_GET_CONTENT
            intent.action = Intent.ACTION_PICK
            intent.putExtra("return-data", true)
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            return intent
        }
    }





}
