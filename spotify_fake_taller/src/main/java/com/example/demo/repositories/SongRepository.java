package com.example.demo.repositories;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.demo.models.entities.Song;

public interface SongRepository extends ListCrudRepository<Song, UUID>{
	
	Optional<Song> findById(UUID code);
	
	List<Song> findByTitle(String title);
	
	List<Song> findByDuration(Integer duration);

	List<Song> findAllByOrderByDurationAsc();

	List<Song> findAllByOrderByDurationDesc();
	
	List<Song> findByDurationBetween(Integer start, Integer end);
}
