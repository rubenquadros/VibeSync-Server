package io.github.rubenquadros.vibesync.test.data

import io.github.rubenquadros.kovibes.api.response.Album
import io.github.rubenquadros.kovibes.api.response.Albums
import io.github.rubenquadros.kovibes.api.response.Artist
import io.github.rubenquadros.kovibes.api.response.ArtistTopTracks
import io.github.rubenquadros.kovibes.api.response.Error
import io.github.rubenquadros.kovibes.api.response.ErrorBody
import io.github.rubenquadros.kovibes.api.response.Playlist
import io.github.rubenquadros.kovibes.api.response.Playlists
import io.github.rubenquadros.kovibes.api.response.RelatedArtists
import io.github.rubenquadros.kovibes.api.response.SpotifyApiResponse
import io.github.rubenquadros.kovibes.api.response.Track

val errorResponse = SpotifyApiResponse.Error(
    body = ErrorBody(
        error = Error(
            status = 500, message = "Something went wrong."
        )
    )
)

val featuredPlaylistsResponse = SpotifyApiResponse.Success(
    result = Playlists(
        isNext = false,
        items = getPlaylists()
    )
)

val artist = SpotifyApiResponse.Success(
    result = artist1
)

val albums = SpotifyApiResponse.Success(
    result = Albums(
        isNext = false,
        items = getAlbums()
    )
)

val artistTopTracks = SpotifyApiResponse.Success(
    result = ArtistTopTracks(
        tracks = geTracks()
    )
)

val relatedArtists = SpotifyApiResponse.Success(
    result = RelatedArtists(
        artists = listOf(artist2)
    )
)

private val artist1 get() = Artist(
    followers = 1000,
    name = "Taylor Swift",
    id = "567",
    popularity = 100,
    genres = emptyList(),
    images = emptyList()
)

private val artist2 get() = Artist(
    followers = 500,
    name = "Future",
    id = "678",
    popularity = 60,
    genres = emptyList(),
    images = emptyList()
)

private val album1 get() = Album(
    albumType = "album",
    albumGroup = "album",
    name = "Album1",
    id = "123",
    totalTracks = 10,
    releaseDate = "2024-02-02",
    availableMarkets = availableMarkets,
    images = emptyList(),
    restrictions = null,
    artists = listOf(artist1)
)

private val album2 get() = Album(
    albumType = "album",
    albumGroup = "album",
    name = "Album2",
    id = "456",
    totalTracks = 15,
    releaseDate = "2024-03-03",
    availableMarkets = availableMarkets,
    images = emptyList(),
    restrictions = null,
    artists = listOf(artist1)
)

private val availableMarkets get() = listOf("US", "ES")

private fun getPlaylists(): List<Playlist> = listOf(
    Playlist(
        collaborative = false,
        description = "D1",
        id = "123",
        name = "Playlist1",
        images = emptyList(),
        public = true
    ),
    Playlist(
        collaborative = false,
        description = "D2",
        id = "456",
        name = "Playlist2",
        images = emptyList(),
        public = true
    )
)

private fun getAlbums(): List<Album> = listOf(album1, album2)

private fun geTracks(): List<Track> = listOf(
    Track(
        name = "Track1",
        id = "123",
        explicit = false,
        popularity = 90,
        previewUrl = "https://track1.preview_url",
        discNumber = 1,
        duration = 2000,
        restrictions = null,
        album = album1,
        artists = listOf(artist1),
        availableMarkets = availableMarkets,
        addedAt = "2024-02-02"
    ),
    Track(
        name = "Track2",
        id = "123",
        explicit = false,
        popularity = 40,
        previewUrl = "https://track1.preview_url",
        discNumber = 1,
        duration = 1000,
        restrictions = null,
        album = album2,
        artists = listOf(artist1),
        availableMarkets = availableMarkets,
        addedAt = "2024-03-03"
    )
)