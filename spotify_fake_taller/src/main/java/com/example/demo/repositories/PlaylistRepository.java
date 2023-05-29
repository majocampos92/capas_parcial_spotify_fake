package com.example.demo.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.demo.models.entities.Playlist;

public interface PlaylistRepository 
	extends ListCrudRepository<Playlist, UUID>{
	
	List<Playlist> findByTitle(String title);
}
