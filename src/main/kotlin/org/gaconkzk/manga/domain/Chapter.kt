package org.gaconkzk.manga.domain

/**
 *
 */
data class Chapter(val idx: Int,
                   val name: String?,
                   val chapters: List<Page>)