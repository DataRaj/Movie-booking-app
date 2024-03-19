package com.dat.proj.backend.repository;

import java.util.List;

import com.dat.proj.backend.entity.Screen;
import com.dat.proj.backend.entity.ScreenPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ScreenRepository extends JpaRepository<Screen, ScreenPk> {

	@Query(value = " select * from screen where theater_id=:theaterId and movie_id = :movieId", nativeQuery = true)
	List<Screen> findScreenByTheaterIdAndMovieId(@Param("theaterId") String theaterId, @Param("movieId") String movieId);

}
