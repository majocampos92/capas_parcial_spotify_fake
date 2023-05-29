package com.example.demo.services;

import java.util.List;

import com.example.demo.models.dtos.SongDTO;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;

public interface SongService {
	
	void save(SongDTO info) throws Exception; 
	
	void deleteById(String code) throws Exception;
	
	List<Song> findAll(); 
	
	Song findById(String code);

	List<Song> findByTitle(String code);
	
	List<Song> findByDuration(Integer duration);
	
	List<Song> findAllByOrderByDurationAsc();

	List<Song> findAllByOrderByDurationDesc();
	
	List<Song> findByDurationBetween(Integer start, Integer end);
}
