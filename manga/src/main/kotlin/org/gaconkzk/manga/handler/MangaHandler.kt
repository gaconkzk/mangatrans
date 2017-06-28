package org.gaconkzk.manga.handler

import io.minio.MinioClient
import org.gaconkzk.manga.repository.MangaRepository
import org.gaconkzk.manga.util.disableCors
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body

/**
 *
 */
@Component
class MangaHandler(val mangarepo: MangaRepository, val minioclient: MinioClient) {
  fun all(req: ServerRequest) = ok().disableCors().body(mangarepo
      .findAll().map { it.copy(url = setUrl(it.name, 0)) })

  private fun setUrl(mangaName: String, page: Int): String? =
      try {
        minioclient
            .presignedGetObject(mangaName, "v01/" + String.format("%03d", page) + ".jpg", 60 * 60 * 24)
      } catch (e: Exception) {
        null
      }
}
