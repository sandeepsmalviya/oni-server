package com.oni.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oni.server.model.impl.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
