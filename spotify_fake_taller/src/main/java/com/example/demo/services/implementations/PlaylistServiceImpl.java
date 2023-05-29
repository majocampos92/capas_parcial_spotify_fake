package com.example.demo.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.User;
import com.example.demo.repositories.PlaylistRepository;
import com.example.demo.services.PlaylistService;

import jakarta.transaction.Transactional;

@Service
public class PlaylistServiceImpl implements PlaylistService {
	
	@Autowired
	private PlaylistRepository playlistRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SavePlaylistDTO info, User userCode) throws Exception {
		Playlist newPlaylist = new Playlist();
		newPlaylist.setTitle(info.getTitle());
		newPlaylist.setDescription(info.getDescription());
		newPlaylist.setUserCode(userCode);
		
		playlistRepository.save(newPlaylist);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);
		playlistRepository.deleteById(code);
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<Playlist> findAll() {
		return playlistRepository.findAll();
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Playlist findOneById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return playlistRepository.findById(code)
					.orElse(null);
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public List<Playlist> findByTitle(String title) {
		try {
			return playlistRepository.findByTitle(title);
		} catch (Exception e) {
			return null;
		}
	}
	
}
