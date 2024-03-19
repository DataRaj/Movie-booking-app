package com.dat.proj.backend.repository;

import java.util.UUID;

import com.dat.proj.backend.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, UUID>{

}
