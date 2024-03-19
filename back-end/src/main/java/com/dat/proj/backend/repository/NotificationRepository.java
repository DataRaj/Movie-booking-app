package com.dat.proj.backend.repository;

import java.util.UUID;

import com.dat.proj.backend.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, UUID>{

}
