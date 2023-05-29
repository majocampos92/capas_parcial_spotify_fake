package com.example.demo.services;

import java.util.List;

import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.User;

public interface PlaylistService {
	void save(SavePlaylistDTO info, User userCode) throws Exception;
	void deleteById(String code) throws Exception;
	List<Playlist> findAll();
	Playlist findOneById(String id);
	List<Playlist> findByTitle(String title);
}
