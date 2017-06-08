package org.gaconkzk.manga.handler

import io.minio.MinioClient
import org.gaconkzk.manga.repository.MangaRepository
import org.gaconkzk.manga.util.disableCors
import org.gaconkzk.manga.util.image
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.body
import reactor.core.publisher.Mono
import java.io.InputStream

/**
 *
 */
@Component
class MangaHandler(val mangarepo: MangaRepository, val minioclient: MinioClient) {
    fun all(req: ServerRequest) = ok().disableCors().body(mangarepo
            .findAll())

    fun img(req: ServerRequest) = ok().disableCors().image().body(Mono.just(
            minioclient
                .getObject(
                        req.queryParam("name").get(),
                        req.queryParam("vol").get()+"/"+
                                req.queryParam("page").get())))

}