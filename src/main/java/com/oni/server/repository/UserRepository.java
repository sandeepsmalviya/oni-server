package com.oni.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oni.server.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
