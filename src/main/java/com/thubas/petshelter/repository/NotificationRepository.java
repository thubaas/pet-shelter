package com.thubas.petshelter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thubas.petshelter.models.Notication;

@Repository
public interface NotificationRepository extends JpaRepository<Notication, Long> {

}
