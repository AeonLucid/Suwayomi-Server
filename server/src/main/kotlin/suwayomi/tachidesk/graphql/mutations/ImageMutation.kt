package suwayomi.tachidesk.graphql.mutations

import suwayomi.tachidesk.manga.impl.util.storage.ImageResponse
import suwayomi.tachidesk.server.ApplicationDirs
import uy.kohesive.injekt.injectLazy

private val applicationDirs: ApplicationDirs by injectLazy()

class ImageMutation {
    data class ClearCachedImagesInput(
        val clientMutationId: String? = null,
        val downloadedThumbnails: Boolean? = null,
        val cachedThumbnails: Boolean? = null,
        val cachedPages: Boolean? = null,
    )

    data class ClearCachedImagesPayload(
        val clientMutationId: String? = null,
        val downloadedThumbnails: Boolean?,
        val cachedThumbnails: Boolean?,
        val cachedPages: Boolean?,
    )

    fun clearCachedImages(input: ClearCachedImagesInput): ClearCachedImagesPayload {
        val (clientMutationId, downloadedThumbnails, cachedThumbnails, cachedPages) = input

        val downloadedThumbnailsResult =
            if (downloadedThumbnails == true) {
                ImageResponse.clearImages(applicationDirs.thumbnailDownloadsRoot)
            } else {
                null
            }

        val cachedThumbnailsResult =
            if (cachedThumbnails == true) {
                ImageResponse.clearImages(applicationDirs.tempThumbnailCacheRoot)
            } else {
                null
            }

        val cachedPagesResult =
            if (cachedPages == true) {
                ImageResponse.clearImages(applicationDirs.tempMangaCacheRoot)
            } else {
                null
            }

        return ClearCachedImagesPayload(
            clientMutationId,
            downloadedThumbnails = downloadedThumbnailsResult,
            cachedThumbnails = cachedThumbnailsResult,
            cachedPages = cachedPagesResult,
        )
    }
}
