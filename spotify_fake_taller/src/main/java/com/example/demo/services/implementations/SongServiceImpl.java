package com.example.demo.services.implementations;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.models.dtos.SongDTO;
import com.example.demo.models.entities.Song;
import com.example.demo.repositories.SongRepository;
import com.example.demo.services.SongService;

import jakarta.transaction.Transactional;

@Service
public class SongServiceImpl implements SongService {

	@Autowired
	private SongRepository songRepository;

	@Override
	@Transactional(rollbackOn = Exception.class)
	public void save(SongDTO info) throws Exception {
		Song song = new Song(
				info.getTitle(),
				info.getDuration()
				);
		songRepository.save(song);
	}

	@Override
	public void deleteById(String id) throws Exception {
		UUID code = UUID.fromString(id);
		songRepository.deleteById(code);
	}

	@Override
	public List<Song> findAll() {
		return songRepository.findAll();
	}

	@Override
	public Song findById(String id) {
		try {
			UUID code = UUID.fromString(id);
			return songRepository.findById(code)
					.orElse(null);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public List<Song> findByTitle(String title) {
		try {
			return songRepository.findByTitle(title);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public List<Song> findByDuration(Integer duration) {
		try {
			return songRepository.findByDuration(duration);
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public List<Song> findAllByOrderByDurationAsc() {
		try {
			return songRepository.findAllByOrderByDurationAsc();
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public List<Song> findAllByOrderByDurationDesc() {
		try {
			return songRepository.findAllByOrderByDurationDesc();
		} catch (Exception error) {
			return null;
		}
	}

	@Override
	public List<Song> findByDurationBetween(Integer start, Integer end) {
		try {
			return songRepository.findByDurationBetween(start, end);
		} catch (Exception error) {
			return null;
		}
	}
}
