package com.dat.proj.backend.repository;

import java.util.List;

import com.dat.proj.backend.entity.SeatMatrix;
import com.dat.proj.backend.entity.SeatMatrixPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatMatrixRepository extends JpaRepository<SeatMatrix, SeatMatrixPk> {

	@Query(value = "select * from seat_matrix where movie_id = :movieId and theater_id=:theaterId and screen_starts_at = :screenStartsAt and booked = false ", nativeQuery = true)
	List<SeatMatrix> getSeatMatrixForscreen(@Param("movieId")String movieId, @Param("theaterId")String theaterId, @Param
			("screenStartsAt") String screenStartsAt);

}
