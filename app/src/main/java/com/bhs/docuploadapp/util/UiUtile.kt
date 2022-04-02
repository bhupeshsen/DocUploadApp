package com.bhs.docuploadapp.util

import android.content.Context
import android.widget.Toast


/**
 * Created by Bhupesh Sen on 31/07/20 at 2:16 PM.
 * bhupeshsen11@gmail.com
 * 7974430255
 */


class UiUtile {

    fun fileSizeValidation(fileSize: Long, context: Context): Boolean {
        return if (fileSize <=  10) true
        else {
            Toast.makeText(context, "File size must be less than 10 MB", Toast.LENGTH_SHORT).show()

            false
        }
    }

}