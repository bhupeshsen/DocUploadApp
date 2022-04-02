package com.bhs.docuploadapp.util

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.provider.OpenableColumns
import android.text.TextUtils
import android.util.Log
import java.io.*

object PdfOrDocFileUtils {
    private const val IMAGE_DIRECTORY = "/TeachMe"
    private const val BUFFER_SIZE = 1024 * 2

    fun getFilePathFromURI(context: Context, contentUri: Uri?): String? {
        //copy file and send new file path
        val fileName = getFileName(contentUri)
     //   val wallpaperDirectory = File(Environment.getExternalStorageDirectory().toString() + IMAGE_DIRECTORY
        val wallpaperDirectory = File(context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString() + IMAGE_DIRECTORY)
      //  val wallpaperDirectory = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs()
        }
        if (!TextUtils.isEmpty(fileName)) {
            val copyFile = File(wallpaperDirectory.path + File.separator.toString() + fileName)
            // create folder if not exists
            copy(context, contentUri, copyFile)
            return copyFile.getAbsolutePath()
        }
        return null
    }

    fun getFileName(uri: Uri?): String? {
        if (uri == null) return null
        var fileName: String? = null
        val path: String? = uri.getPath()
        val cut = path?.lastIndexOf('/')
        if (cut != -1) {
            fileName = path?.substring(cut!! + 1)
        }
        return fileName
    }

    fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
        try {
            val inputStream: InputStream = context.getContentResolver().openInputStream(srcUri!!)
                ?: return
            val outputStream: OutputStream = FileOutputStream(dstFile)
            copystream(inputStream, outputStream)
            inputStream.close()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Throws(Exception::class, IOException::class)
    fun copystream(input: InputStream?, output: OutputStream?): Int {
        val buffer = ByteArray(BUFFER_SIZE)
        val `in` = BufferedInputStream(input, BUFFER_SIZE)
        val out = BufferedOutputStream(output, BUFFER_SIZE)
        var count = 0
        var n = 0
        try {
            while (`in`.read(buffer, 0, BUFFER_SIZE)
                    .also { n = it } != -1
            ) {
                out.write(buffer, 0, n)
                count += n
            }
            out.flush()
        } finally {
            try {
                out.close()
            } catch (e: IOException) {
                Log.e(e.message, java.lang.String.valueOf(e))
            }
            try {
                `in`.close()
            } catch (e: IOException) {
                Log.e(e.message, java.lang.String.valueOf(e))
            }
        }
        return count
    }

    fun getFileSize(context: Context,path: Uri?) : Long{
        val returnCursor: Cursor? = context.contentResolver.query(path!!, null, null, null, null)
        val nameIndex = returnCursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        val sizeIndex = returnCursor?.getColumnIndex(OpenableColumns.SIZE)
        returnCursor?.moveToFirst()
        val size: Long = sizeIndex?.let { returnCursor?.getLong(it) } ?: 0
        val sizeInMB: Long = (size / 1024) / 1000
        returnCursor?.close()
        Log.d("TAG", "getFileSize: "+sizeInMB)
        return sizeInMB
    }


    @SuppressLint("Range")
     fun getCorrectFileName(context: Context, uri : Uri) : String{

        var uriString = uri.toString()
        var displayName : String = ""
        val myFile = File(uriString)
        if (uriString.startsWith("content://")) {
            var cursor: Cursor? = null
            try {
                cursor = context.getContentResolver().query(uri, null, null, null, null)
                if (cursor != null && cursor.moveToFirst()) {
                    displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                }
            } finally {
                cursor?.close()
            }
        } else if (uriString.startsWith("file://")) {
            displayName = myFile.getName()
        }
        Log.d("TAG", "getFileName: ")
        return  displayName
    }

}