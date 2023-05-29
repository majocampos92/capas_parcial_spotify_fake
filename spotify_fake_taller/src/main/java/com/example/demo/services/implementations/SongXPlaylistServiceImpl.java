package com.example.demo.services.implementations;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.SongXPlaylistSaveDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.repositories.SongXPlaylistRepository;
import com.example.demo.services.SongXPlaylistService;

import jakarta.transaction.Transactional;

@Service
public class SongXPlaylistServiceImpl implements SongXPlaylistService{

	@Autowired
	private SongXPlaylistRepository songXplaylistRepository;
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createSongXPlaylist(Date date, Playlist playlist, Song song) throws Exception {
		SongXPlaylist songXplaylist = new SongXPlaylist(
					song,
					playlist,
					date
				);
		songXplaylistRepository.save(songXplaylist);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
			UUID code = UUID.fromString(id);
			songXplaylistRepository.deleteById(code);
	}
}