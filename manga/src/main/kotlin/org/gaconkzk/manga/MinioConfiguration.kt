package org.gaconkzk.manga

import io.minio.MinioClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 *
 */
@Configuration
class MinioConfiguration(
        @Value("\${minio.url}") val minioUrl: String,
        @Value("\${minio.accessKey}") val minioAccessKey: String,
        @Value("\${minio.secretKey}") val minioSecretKey: String) {
    @Bean
    fun minioClient(): MinioClient = MinioClient(minioUrl,
            minioAccessKey, minioSecretKey)
}