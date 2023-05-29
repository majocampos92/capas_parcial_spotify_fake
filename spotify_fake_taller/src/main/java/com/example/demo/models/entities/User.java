package com.example.demo.models.entities;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "user", schema = "public")
@ToString(exclude = {"userPlaylist"})
public class User {
	
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "username")
	private String username;
	
	@Column(name = "email")
	private String email;

	@Column(name = "password")
	@JsonIgnore
	private String password;
	
	@OneToMany(mappedBy = "userCode", fetch = FetchType.LAZY)
	@JsonIgnore
	private List<Playlist> userPlaylist;
	
	public User(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
	}
}
