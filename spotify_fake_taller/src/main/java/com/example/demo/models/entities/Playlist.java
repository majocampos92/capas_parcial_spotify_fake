package com.example.demo.models.entities;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name = "playlist")
@NoArgsConstructor
@ToString(exclude = {"playlistSongs"})
public class Playlist {
	
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "description")
	private String description;
	
	@JoinColumn(name = "user_code", nullable = true)
	@ManyToOne(fetch = FetchType.EAGER)
	private User userCode;
	
	@OneToMany(mappedBy = "playlistCode", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<SongXPlaylist> playlistSongs;

	public Playlist(String title, String description, User userCode) {
		super();
		this.title = title;
		this.description = description;
		this.userCode = userCode;
	}
}
