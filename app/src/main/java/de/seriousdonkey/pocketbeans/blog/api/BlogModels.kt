package de.seriousdonkey.pocketbeans.blog.api

import org.joda.time.DateTime

data class BlogResponse(
        val success: Boolean,
        val pagination: BlogPagination,
        val data: List<BlogData>
)

data class BlogPagination(val offset: Int, val limit: Int, val total: Int)

data class BlogData(
        val id: Int,
        val title: String,
        val subtitle: String,
        val thumbImage: List<Image>,
        val isSponsored: Boolean,
        val publishDate: DateTime
)

data class Image (
        val name: String,
        val url: String,
        val width: Int,
        val height: Int
)