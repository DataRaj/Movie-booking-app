package com.dat.proj.backend.repository;

import com.dat.proj.backend.entity.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long>{

}
