package com.example.demo.services;

import java.util.Date;

import com.example.demo.models.dtos.SongXPlaylistSaveDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;

public interface SongXPlaylistService {
	void createSongXPlaylist(Date date, Playlist playlist, Song song) throws Exception;
	void deleteById(String id) throws Exception;
}
