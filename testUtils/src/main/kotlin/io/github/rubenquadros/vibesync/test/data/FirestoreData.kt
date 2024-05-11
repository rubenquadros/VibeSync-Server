package io.github.rubenquadros.vibesync.test.data

import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.TopEntity

val firestoreErrorResponse = FirestoreApiResponse.Error(throwable = Exception("Error fetching firestore data"))

val topEntityResponse = FirestoreApiResponse.Success(topEntity)

val topEntity get() = listOf(
    TopEntity(
        id = "123",
        name = "Top Entity1",
        image = "https://topentity1.image1.png"
    ),
    TopEntity(
        id = "456",
        name = "Top Entity2",
        image = "https://topentity2.image2.png"
    )
)