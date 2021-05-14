package com.stalion73.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;


@Entity
@Audited
@Table(name = "users")
public class User{
	@Id
	String username;
	
	String password;

	String token;
	
	Boolean enabled;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	@NotAudited
	@JsonIgnore
	private Set<Authorities> authorities;

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean isEnabled() {
		return this.enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getToken(){
		return this.token;
	}
	
	public void setToken(String token){
		this.token = token;
	}

	public Set<Authorities> getAuthorities() {
		return this.authorities;
	}

	public void setAuthorities(Set<Authorities> authorities) {
		this.authorities = authorities;
	}	
}
