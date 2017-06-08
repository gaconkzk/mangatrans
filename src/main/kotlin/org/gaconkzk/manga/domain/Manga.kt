package org.gaconkzk.manga.domain

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.PersistenceConstructor
import org.springframework.data.mongodb.core.mapping.Document

/**
 *
 */
@Document
data class Manga @PersistenceConstructor constructor(@Id val id: String,
                                                     val name: String,
                                                     val author: String,
                                                     val status: String,
                                                     val volumes: Int,
                                                     val chapters: Int,
                 val lang: String)