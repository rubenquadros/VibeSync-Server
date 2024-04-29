package io.github.rubenquadros.vibesync.server.search

import io.ktor.resources.Resource
import io.ktor.server.application.call
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import org.koin.ktor.ext.inject

fun Route.searchRoute() {

    val searchApi by inject<SearchApi>()

    get<Search.Track> { track ->
        val response = searchApi.searchTrack(track.query, track.offset, track.limit)
        call.respond(status = response.status, message = response.data)
    }

    get<Search.Album> { album ->
        val response = searchApi.searchAlbum(album.query, album.offset, album.limit)
        call.respond(status = response.status, message = response.data)
    }

    get<Search.Artist> { artist ->
        val response = searchApi.searchArtist(artist.query, artist.offset, artist.limit)
        call.respond(status = response.status, message = response.data)
    }

    get<Search.Playlist> { playlist ->
        val response = searchApi.searchPlaylist(playlist.query, playlist.offset, playlist.limit)
        call.respond(status = response.status, message = response.data)
    }
}

@Resource("/search")
private class Search {

    @Resource("/track")
    data class Track(val parent: Search, val query: String, val offset: Int, val limit: Int)

    @Resource("/album")
    data class Album(val parent: Search, val query: String, val offset: Int, val limit: Int)

    @Resource("/artist")
    data class Artist(val parent: Search, val query: String, val offset: Int, val limit: Int)

    @Resource("/playlist")
    data class Playlist(val parent: Search, val query: String, val offset: Int, val limit: Int)
}