package com.example.demo.controllers;

import java.util.List;
import java.util.UUID;
import java.text.SimpleDateFormat;  
import java.util.Date;  

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.dtos.ErrorsDTO;
import com.example.demo.models.dtos.MessageDTO;
import com.example.demo.models.dtos.SavePlaylistDTO;
import com.example.demo.models.dtos.SongXPlaylistSaveDTO;
import com.example.demo.models.entities.Playlist;
import com.example.demo.models.entities.Song;
import com.example.demo.models.entities.SongXPlaylist;
import com.example.demo.models.entities.User;
import com.example.demo.services.PlaylistService;
import com.example.demo.services.SongService;
import com.example.demo.services.SongXPlaylistService;
import com.example.demo.services.UserService;
import com.example.demo.utils.ErrorHandlers;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/playlist")
@CrossOrigin("*")
public class PlaylistController {

	@Autowired
	private PlaylistService playlistService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private SongXPlaylistService songXplaylistService;
	
	@Autowired
	private ErrorHandlers errorHandler;
	
	@GetMapping("/all")
	public ResponseEntity<?> findAllPlaylists() {
		List<Playlist> playlists = playlistService.findAll();
		return new ResponseEntity<>(playlists, HttpStatus.OK);
	}
	
	@GetMapping("/{code}")
	public ResponseEntity<?> findOnePlaylist(@PathVariable(name = "code") UUID code) {
		Playlist playlist = playlistService.findOneById(code.toString());
		
		if (playlist == null) {
			return new ResponseEntity<>(
					new MessageDTO("Playlist not found"), HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(playlist, HttpStatus.OK);
	}
	
	@PostMapping("/")
	public ResponseEntity<?> savePlaylist(@ModelAttribute @Valid SavePlaylistDTO info, User userCode, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		} 

		User userFound = userService.findOneByID(userCode.getCode().toString());
		
		if(userFound == null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		try {
			
			playlistService.save(info, userFound);

			System.out.println(userCode);
			return new ResponseEntity<>(
					new MessageDTO("Playlist created"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/delete/{code}")
	public ResponseEntity<?> deletePlaylistById(@PathVariable(name = "code") UUID code) {
		
		Playlist playlistFound = playlistService.findOneById(code.toString());
		
		if (playlistFound == null) {
			return new ResponseEntity<>(new MessageDTO("Playlist not found"), HttpStatus.NOT_FOUND);
		}
		
		try {
			playlistService.deleteById(playlistFound.getCode().toString());
			return new ResponseEntity<>(new MessageDTO("Playlist deleted"), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} 
	
	@GetMapping("/search/{title}")
	public ResponseEntity<?> findByTitle(@PathVariable(name = "title") String title) {
		List<Playlist> playlistsByTitle = playlistService.findByTitle(title);
		
		if (playlistsByTitle == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(
				playlistsByTitle, HttpStatus.OK
			);
	}
	
	@GetMapping("/songs/{code}")
	public ResponseEntity<?> searchPlaylistWithTheSong(@PathVariable String code) {
		Playlist playlist = playlistService.findOneById(code);
		
		if(playlist == null) 
			return new ResponseEntity<>("playlist not found", HttpStatus.NOT_FOUND);
		
		List<SongXPlaylist> songXplaylist = playlist.getPlaylistSongs();
		return new ResponseEntity<>(songXplaylist, HttpStatus.OK);
	}
	
	@PostMapping("/add/song")
	public ResponseEntity<?> addSongToPlaylist(@ModelAttribute @Valid SongXPlaylistSaveDTO data, BindingResult validations){
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()),
					HttpStatus.BAD_REQUEST);
		} 
		Song song = songService.findById(data.getSongCode());
		Playlist playlist = playlistService.findOneById(data.getPlaylistCode());
		
		if(song == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		if(playlist == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");  
	    Date date = new Date(); 
		
		try {
			songXplaylistService.createSongXPlaylist(date, playlist, song);
			return new ResponseEntity<>(
					new MessageDTO("Song Added"), HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(
					new MessageDTO("Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
