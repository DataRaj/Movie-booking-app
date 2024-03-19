package com.dat.proj.backend.repository;

import java.util.List;

import com.dat.proj.backend.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface MovieRepository extends JpaRepository<Movie, String>
{
	

	@Query(value = "select m.* from movie m join theater t on  t.theater_id = m.theater_id and t.city  = :city",  nativeQuery = true)
	List<Movie> getAvailableMovies(@Param("city")String city);

}
