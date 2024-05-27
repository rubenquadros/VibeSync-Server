package io.github.rubenquadros.vibesync.firestore

import com.google.cloud.firestore.DocumentSnapshot
import com.google.cloud.firestore.Query
import com.google.cloud.firestore.QueryDocumentSnapshot

class Pager {

    companion object {
        private const val PAGE_SIZE = 20
    }

    suspend fun getPage(offset: Int, query: Query): PageResponse {
        var lastDocument: DocumentSnapshot? = null
        val totalDocumentsToSkip = offset * PAGE_SIZE

        if (totalDocumentsToSkip > 0) {
            val numberOfBatches = totalDocumentsToSkip / PAGE_SIZE

            repeat(numberOfBatches) {
                val q = if (lastDocument != null) {
                    query.limit(PAGE_SIZE).startAfter(lastDocument as DocumentSnapshot)
                } else {
                    query.limit(PAGE_SIZE)
                }

                val queryDocumentSnapshot = q.await()
                if (queryDocumentSnapshot.isEmpty()) return PageResponse()
                lastDocument = queryDocumentSnapshot.lastOrNull()
            }
        }

        val finalQuery = if (lastDocument != null) {
            query.limit(PAGE_SIZE + 1).startAfter(lastDocument as DocumentSnapshot)
        } else {
            query.limit(PAGE_SIZE + 1)
        }

        val querySnapshot = finalQuery.await()
        return PageResponse(
            isNext = querySnapshot.size > PAGE_SIZE,
            page = querySnapshot.take(PAGE_SIZE)
        )
    }

}

data class PageResponse(
    val isNext: Boolean = false,
    val page: List<QueryDocumentSnapshot> = emptyList()
)