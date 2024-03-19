package com.dat.proj.backend.repository;

import java.util.List;

import com.dat.proj.backend.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends JpaRepository<Theatre, String> {

	@Query(value = "select distinct(city) from theater", nativeQuery = true)
	List<String> getSupportdCities();
	
	@Query(value = "select * from theater where theater_id = :theaterId and city = :city", nativeQuery = true)
	List<Theatre> getScreensShowingMovie(@Param("theaterId")String theaterId, @Param("city")String city);

}
