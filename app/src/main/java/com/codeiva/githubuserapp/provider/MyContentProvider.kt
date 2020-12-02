package com.codeiva.githubuserapp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.codeiva.githubuserapp.data.AppDatabase
import com.codeiva.githubuserapp.data.dao.FavoriteUserDao
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class MyContentProvider : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val AUTHORITY = "com.codeiva.githubuserapp.provider"
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH).apply {
            addURI(AUTHORITY, "users", USER)
        }
    }

    private val favoriteUserDao: FavoriteUserDao by lazy {
        AppDatabase.getInstance(requireNotNull(context)).favoriteUserDao()
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        throw UnsupportedOperationException()
    }

    override fun getType(uri: Uri): String? {
        throw UnsupportedOperationException()
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        throw UnsupportedOperationException()
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ): Cursor? {
        return when (uriMatcher.match(uri)) {
            USER -> favoriteUserDao.cursorGetAll()
            else -> null
        }
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        throw UnsupportedOperationException()
    }
}
