package com.dat.proj.backend.repository;

import java.util.List;

import com.dat.proj.backend.entity.Cast;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface CastRepository extends JpaRepository<Cast, Long>{
	@Query(value = "select * from cast where movie_id=:movieId", nativeQuery=true)
	List<Cast> getCastByMovieId(@Param("movieId") String movieId);

}
