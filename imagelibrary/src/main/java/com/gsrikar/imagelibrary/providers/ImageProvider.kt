package com.gsrikar.imagelibrary.providers

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri


/**
 * Dummy content provider to avoid initialization of the library and get the context
 */
class ImageProvider : ContentProvider() {

    companion object {
        var appContext: Context? = null
    }

    override fun onCreate(): Boolean {
        // Get the context
        this.context?.let {
            appContext = it
            return true
        }
        return false
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }
}
