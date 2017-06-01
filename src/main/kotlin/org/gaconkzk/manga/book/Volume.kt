package org.gaconkzk.manga.book

/**
 *
 */
data class Volume(val idx: Int,
                  val name: String?,
                  val chapters: List<Chapter>?,
                  val pages: List<Page>?)