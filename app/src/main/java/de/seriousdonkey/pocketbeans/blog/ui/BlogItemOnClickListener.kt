package de.seriousdonkey.pocketbeans.blog.ui

import de.seriousdonkey.pocketbeans.blog.ui.models.BlogEntry

interface BlogItemOnClickListener {
    fun onItemClicked(entry: BlogEntry)
}