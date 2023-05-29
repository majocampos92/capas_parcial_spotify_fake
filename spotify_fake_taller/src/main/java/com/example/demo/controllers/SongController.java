package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.ErrorsDTO;
import com.example.demo.models.dtos.MessageDTO;
import com.example.demo.models.dtos.SongDTO;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.services.SongService;
import com.example.demo.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/Song")
@CrossOrigin("*")
public class SongController {

	@Autowired
	private SongService songService;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	//CREATE SONG 
	@PostMapping("/")
	public ResponseEntity<?> saveSong(@ModelAttribute @Valid SongDTO info, BindingResult validations) {
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
					HttpStatus.BAD_REQUEST
					);
		}
		try {
			songService.save(info);
			return new ResponseEntity<>(new MessageDTO("Song Created"), HttpStatus.CREATED);
		} catch (Exception error) {
			error.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//GET ALL SONGS 
	@GetMapping("/songs")
	public ResponseEntity<?> findAll() {
		List<Song> songs = songService.findAll();
		return new ResponseEntity<>(songs, HttpStatus.OK);
	}
	
	//GET SONG BY ID 
	@GetMapping("/{id}")
	public ResponseEntity<?> findSongById(@PathVariable(name = "id") String code) {
		Song foundSong = songService.findById(code);
		
		if(foundSong == null) {
			return new ResponseEntity<>(new MessageDTO("Song Not Found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(foundSong, HttpStatus.OK);
	}
	
	//DELETE SONG BY ID 
	@PostMapping("/delete/{id}")
	public ResponseEntity<?> deleteSongByID(@PathVariable(name = "id") String code, BindingResult validations) {
		if(validations.hasErrors()) {
			return new ResponseEntity<>(new ErrorsDTO(
					errorHandler.mapErrors(validations.getFieldErrors())), 
					HttpStatus.BAD_REQUEST
					);
		}
		try {
			songService.deleteById(code);
			return new ResponseEntity<>(new MessageDTO("Deleted Song"), HttpStatus.OK);
		} catch (Exception error) {
			error.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//GET ALL SONGS BY TITLE  
	@GetMapping("/songs/{title}")
	public ResponseEntity<?> findAllSongsByTitle(@PathVariable(name = "title") String title) {
		List<Song> songsByTitle = songService.findByTitle(title);
		return new ResponseEntity<>(
				songsByTitle, HttpStatus.OK
			);
	}
	
	//GET ORDER BY DURATION 
	@GetMapping("/songs/asc")
	public  ResponseEntity<?> searchOrderByDurationAsc() {
		List<Song> songs = songService.findAllByOrderByDurationAsc();
		return new ResponseEntity<>(songs, HttpStatus.OK);
	}
	
	@GetMapping("/songs/desc")
	public  ResponseEntity<?> searchOrderByDurationDesc() {
		List<Song> songs = songService.findAllByOrderByDurationDesc();
		return new ResponseEntity<>(songs, HttpStatus.OK);
	}
	
	@GetMapping("/songs/between")
	@ResponseBody
	public  ResponseEntity<?> searchSongBetweenDuration(
			@RequestParam(name = "start") Integer start, 
			@RequestParam(name = "end") Integer end) {
			List<Song> songs = songService.findByDurationBetween(start, end);
			return new ResponseEntity<>(songs, HttpStatus.OK);
	}
	
	@GetMapping("/inplaylist/{code}")
	public ResponseEntity<?> searchPlaylistWithTheSong(@PathVariable String code) {
		Song song = songService.findById(code);
		
		if(song == null) 
			return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
		
		List<SongXPlaylist> songXplaylist = song.getPlaylistSongs();
		return new ResponseEntity<>(songXplaylist, HttpStatus.OK);
	}
	
	
	
	
	
	
	
	
}
