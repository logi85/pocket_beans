package de.seriousdonkey.pocketbeans.info.services

import android.content.Context

class InfoService(private val _context: Context) {

    fun getAppVersion() : String {
        return _context.packageManager.getPackageInfo(_context.packageName, 0).versionName
    }

}