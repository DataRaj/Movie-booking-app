package com.dat.proj.backend.repository;

import com.dat.proj.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{

	@Query(value = "select * from user where user_name = :userName", nativeQuery = true)
	User findUserByUserName(@Param("userName") String userName);
	
	
	
}
