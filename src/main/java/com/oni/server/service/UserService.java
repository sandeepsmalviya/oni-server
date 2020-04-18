package com.oni.server.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oni.server.model.impl.User;
import com.oni.server.repository.UserRepository;

@Service
public class UserService {

	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		return userRepository.save(user);
	}

	public User update(String userId, User user) {
		return userRepository.save(user);
	}

	public void delete(int userId) {
		userRepository.deleteById(userId);
	}

	public void deleteAll() {
		userRepository.deleteAll();
	}

	public long countAll() {
		return userRepository.count();
	}

	public List<User> findAll() {
		return userRepository.findAll();
	}

	public User findById(Integer userId) {
		Optional<User> optional = userRepository.findById(userId);
		return optional.get();
	}

	public User findOne() {
		if (this.findAll() == null || this.findAll().isEmpty()) {
			return null;
		} else {
			return this.findAll().get(0);
		}

	}

}
