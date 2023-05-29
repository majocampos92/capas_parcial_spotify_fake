package com.example.demo.services;

import com.example.demo.models.dtos.SongXPlaylistSaveDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;

public interface SongXPlaylistService {
	void createSongXPlaylist(SongXPlaylistSaveDTO info, Playlist playlistCode, Song songCode) throws Exception;
	void deleteById(String id) throws Exception;
}
