package com.oni.server.endpoint;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.oni.server.model.Student;
import com.oni.server.model.User;
import com.oni.server.model.Users;
import com.oni.server.service.UserService;

import io.swagger.annotations.Api;

@RestController
@Validated
@RequestMapping("/api")
@Api(value = "/api", description = " It will provide API for CRUD operatings for User details")
@CrossOrigin(origins = "*")
public class UserResource {

	Logger logger = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	UserService userService;

	@GetMapping("/greeting/{userId}")
	public Student greetingMessage(@PathVariable String userId,  @RequestParam @Min(2) Integer parameter1, @RequestParam @Min(2) Integer parameter2,
			@Valid @RequestBody Student student1) {
		return new Student(userId, parameter1);
	}

	@GetMapping("/greeting")
	public String greeting() {
		boolean b = true;
		String num = "10";

		// try {
		int a = (Integer.parseInt(num));

		if (b) {
			throw new NullPointerException("Greeting exception called");
		}
		return "Hello, User";
	}

	@GetMapping("/user/one")
	public ResponseEntity<User> findOne() {

		User user = null;
		List<User> userList = userService.findAll();

		if (userList == null || userList.isEmpty()) {
			user = null;
		} else {
			user = userList.get(0);
		}

		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	
	@GetMapping("/user/{userId}")
	public ResponseEntity<User> findById(@PathVariable Integer userId) {
		User user = userService.findById(userId);
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	
	@GetMapping("/user")
	public ResponseEntity<Users> finAll() {
		Users users = new Users(userService.findAll());
		return new ResponseEntity<Users>(users, HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<User> save(@RequestBody User user) {
		User userValue = userService.save(user);
		return new ResponseEntity<User>(userValue, HttpStatus.CREATED);
	}

	
	@PutMapping("/user/{userId}")
	public ResponseEntity<User> update(@PathVariable int userId, @RequestBody User user) {

		User userValue = userService.update(userId, user);
		return new ResponseEntity<User>(userValue, HttpStatus.CREATED);

	}

	@DeleteMapping("/user/{userId}")
	public ResponseEntity<String> delete(@PathVariable int userId) {
		userService.delete(userId);
		return new ResponseEntity<String>("User with userId=" + userId + " is deleted successfully", HttpStatus.OK);
	}

	@DeleteMapping("/user")
	public ResponseEntity<String> deleteAll() {
		userService.deleteAll();
		return new ResponseEntity<String>("All User in database are deleted successfully", HttpStatus.OK);
	}

	@GetMapping("/user/count")
	public ResponseEntity<Long> count() {
		Long count = userService.countAll();
		return new ResponseEntity<Long>(count, HttpStatus.OK);
	}
	
	
	@PostMapping("/user/clearCache")
	public ResponseEntity<String> clareCache() {
		userService.clearUserCache();
		return new ResponseEntity<>("Cache cleared successfully at server", HttpStatus.OK);
	}

}
