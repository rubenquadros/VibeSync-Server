package io.github.rubenquadros.vibesync.test.data

import io.github.rubenquadros.vibesync.firestore.model.FirestoreApiResponse
import io.github.rubenquadros.vibesync.firestore.model.PlaylistInfo
import io.github.rubenquadros.vibesync.firestore.model.TopEntity
import io.github.rubenquadros.vibesync.firestore.model.UserProfile
import io.github.rubenquadros.vibesync.firestore.model.getErrorResponse
import io.github.rubenquadros.vibesync.firestore.model.getSuccessResponse
import io.ktor.http.HttpStatusCode

val firestoreErrorResponse = getErrorResponse(
    statusCode = HttpStatusCode.InternalServerError,
    throwable = Exception("Error fetching firestore data")
)

const val unknownUser = "12345"

val firestoreSuccessNoBodyResponse = FirestoreApiResponse.SuccessNoBody

val topEntityResponse = getSuccessResponse(topEntity)

val userProfileResponse = getSuccessResponse(userProfile)

val likedTracksResponse = getSuccessResponse(listOf(trackInfo))

val likedAlbumsResponse = getSuccessResponse(listOf(mediaInfo))

val userPlaylistsResponse = getSuccessResponse(listOf(userPlaylist))

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

val userProfile get() = UserProfile(
    id = "234",
    name = "Test name",
    email = "testemail@gmail.com"
)

val userPlaylist get() = PlaylistInfo(
    id = "214",
    playlistName = "My Playlist",
    owner = "Test"
)