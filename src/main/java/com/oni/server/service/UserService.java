package com.oni.server.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.oni.server.constants.OniServerConstants;
import com.oni.server.model.User;
import com.oni.server.repository.UserRepository;

@Service
public class UserService {

	Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private UserRepository userRepository;

	public User save(User user) {
		logger.debug("save is called");
		return userRepository.save(user);
	}

	@CachePut(value = OniServerConstants.CACHE_NAME, key = "#userId")
	public User update(int userId, User user) {

		User userValue = null;
		if (userId == user.getUserId()) {
			if (userRepository.findById(userId) == null) {
				throw new EntityNotFoundException("user with userId=" + userId + " does not exists, can not update");
			} else {
				userValue = userRepository.saveAndFlush(user);
			}
		} else {
			throw new IllegalArgumentException(
					"userId = " + userId + " does not match with object user.userId " + user.getUserId());
		}

		logger.debug("update is called for userId=" + userId);
		return userValue;
	}

	@CacheEvict(value = OniServerConstants.CACHE_NAME, key = "#userId")
	public void delete(int userId) {
		logger.debug("delete is called for userId=" + userId);
		userRepository.deleteById(userId);
	}

	@CacheEvict(value = "user-cache", allEntries = true)
	public void deleteAll() {
		logger.debug("deleteAll is called");
		userRepository.deleteAll();
	}

	public long countAll() {
		logger.debug("countAll is called");
		return userRepository.count();
	}

	public List<User> findAll() {
		logger.debug("findAll is called for ");
		return userRepository.findAll();
	}

	@Cacheable(value = OniServerConstants.CACHE_NAME, key = "#userId")
	public User findById(Integer userId) {

		logger.debug("findById with userId = " + userId + "  is called");
		Optional<User> optional = userRepository.findById(userId);
		return optional.get();
	}

	public User findOne() {

		logger.debug("findOne is called for ");
		if (this.findAll() == null || this.findAll().isEmpty()) {
			return null;
		} else {
			return this.findAll().get(0);
		}
	}

	@CacheEvict(value = "user-cache")
	public void clearUserCache() {

	}

}
