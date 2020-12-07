package de.seriousdonkey.pocketbeans.info.ui

import de.seriousdonkey.pocketbeans.info.ui.models.InfoEntry

interface OnInfoItemEntryClickedCallback {
    fun onItemClicked(entry: InfoEntry)
}