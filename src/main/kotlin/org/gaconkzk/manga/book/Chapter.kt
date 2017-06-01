package org.gaconkzk.manga.book

/**
 *
 */
data class Chapter(val idx: Int,
                   val name: String?,
                   val chapters: List<Page>)