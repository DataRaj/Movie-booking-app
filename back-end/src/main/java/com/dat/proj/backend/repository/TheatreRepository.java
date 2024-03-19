package com.dat.proj.backend.repository;

import java.util.List;

import com.dat.proj.backend.entity.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, String> {

	@Query(value = "select distinct(city) from Theatre", nativeQuery = true)
	List<String> getSupportdCities();
	
	@Query(value = "select * from Theatre where Theatre_id = :TheatreId and city = :city", nativeQuery = true)
	List<Theatre> getScreensShowingMovie(@Param("TheatreId")String TheatreId, @Param("city")String city);

}
