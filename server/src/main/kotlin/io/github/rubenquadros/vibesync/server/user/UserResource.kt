package io.github.rubenquadros.vibesync.server.user

import io.ktor.resources.Resource

@Resource("/user")
class User {

    @Resource("/{id}")
    data class Id(val parent: User, val id: String) {
        @Resource("/save")
        data class Save(val parent: Id)

        @Resource("/like-track")
        data class LikeTrack(val parent: Id)

        @Resource("/unlike-track/{trackId}")
        data class UnlikeTrack(val parent: Id, val trackId: String)

        @Resource("/like-album")
        data class LikeAlbum(val parent: Id)

        @Resource("/unlike-album/{albumId}")
        data class UnlikeAlbum(val parent: Id, val albumId: String)

        @Resource("/like-playlist")
        data class LikePlaylist(val parent: Id)

        @Resource("/unlike-playlist/{playlistId}")
        data class UnlikePlaylist(val parent: Id, val playlistId: String)

        @Resource("/liked-tracks")
        data class LikedTracks(val parent: Id)

        @Resource("/liked-albums")
        data class LikedAlbums(val parent: Id)

        @Resource("/playlists")
        data class Playlists(val parent: Id)

        @Resource("/create-playlist")
        data class CreatePlaylist(val parent: Id)

        @Resource("/playlist/{playlistId}")
        data class DeletePlaylist(val parent: Id, val playlistId: String)

        @Resource("/update-playlist/{playlistId}")
        data class UpdatePlaylist(val parent: Id, val playlistId: String)

        @Resource("/playlist/{playlistId}/tracks")
        data class GetPlaylistTracks(val parent: Id, val playlistId: String)
    }
}