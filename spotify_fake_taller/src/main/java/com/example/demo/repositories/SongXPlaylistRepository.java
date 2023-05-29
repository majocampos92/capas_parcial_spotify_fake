package com.example.demo.repositories;

import java.util.UUID;

import org.springframework.data.repository.ListCrudRepository;

import com.example.demo.models.entities.SongXPlaylist;

public interface SongXPlaylistRepository 
extends ListCrudRepository<SongXPlaylist, UUID>{

}