package com.dat.proj.backend.entity;

import java.io.Serializable;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "shows")
public class Show implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "movie_id")
	private Long movieId;

//	@Column(name = "theater_id")
//	private Long theaterId;
//
//	@Column(name = "screen_id")
//	private Long screenId;

	@Column(name = "starts_at", columnDefinition = "DATETIME")
	@Temporal(TemporalType.DATE)
	private Date startsAt;

	@Column(name = "ends_at", columnDefinition = "DATETIME")
	@Temporal(TemporalType.DATE)

	private Date endsAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public Date getStartsAt() {
		return startsAt;
	}

	public void setStartsAt(Date startsAt) {
		this.startsAt = startsAt;
	}

	public Date getEndsAt() {
		return endsAt;
	}

	public void setEndsAt(Date endsAt) {
		this.endsAt = endsAt;
	}

	@Override
	public String toString() {
		return "Show [id=" + id + ", movieId=" + movieId + ", startsAt=" + startsAt + ", endsAt=" + endsAt + "]";
	}

}
