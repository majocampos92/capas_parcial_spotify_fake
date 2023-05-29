package com.example.demo.services.implementations;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.demo.models.dtos.SongXPlaylistSaveDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.repositories.SongXPlaylistRepository;
import com.example.demo.services.SongXPlaylistService;

import jakarta.transaction.Transactional;

public class SongXPlaylistServiceImpl implements SongXPlaylistService{

	@Autowired
	private SongXPlaylistRepository songXplaylistRepository;
	
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public void createSongXPlaylist(SongXPlaylistSaveDTO info, Playlist playlistCode, Song songCode) throws Exception {
		SongXPlaylist songXplaylist = new SongXPlaylist(
					songCode,
					playlistCode,
					info.getDateAdded()
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