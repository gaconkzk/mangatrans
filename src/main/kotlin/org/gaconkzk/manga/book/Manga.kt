package org.gaconkzk.manga.book

/**
 *
 */
data class Manga(val name: String,
                 val completed: Boolean = false,
                 val volumes: List<Volume>,
                 val lang: String)