package com.analyze.controllers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.model.User;
import com.analyze.repositories.UserRepository;

@RestController
@RequestMapping("geo/user")
public class UserController {
	private UserRepository userRepository;
	static Logger log = LogManager.getLogger(UserController.class);

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public User getUserById(@PathVariable("id") Long id) {
		return userRepository.getUserById(id);
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Iterable<User> getAllUsers() {
		return userRepository.findAll();
	}

	@RequestMapping(value = "/points/{id}", method = RequestMethod.GET)
	public Long getUserPoints(@PathVariable("id") Long id) {
		return userRepository.getUserPoints(id);
	}

	@RequestMapping(value = "/points/increase/{id}", method = RequestMethod.POST)
	public int increasePoints(@RequestBody int points, @PathVariable("id") Long id) {
		log.info("Kontorler: " + points);
		log.info("USERID: " + id);
		return userRepository.increasePoints(points, id);
	}

	@RequestMapping(value = "/points/decrease/{id}", method = RequestMethod.POST)
	public Long decreasePoints(@RequestBody int points, @PathVariable("id") Long id) {
		
		int points1 = getUserPoints(id).intValue();
		int poin, checkPoint = points1 - points;
		Long p;
		if (points1 >= 0 && checkPoint >= 0) {
			poin = userRepository.decreasePoints(points, id);
			p = userRepository.getUserPoints(id);
		} else {
			poin = -1;//return this in case that we don't have more points
			p = (long) -1;
		}

		return p;

	}
	
	@RequestMapping(value = "/new", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public User insertNewUser(@RequestBody User user) {
		return userRepository.save(user);
	}
	
	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void deleteUser(@PathVariable long id) {
		log.info("DELETE informacija");
	    	userRepository.delete(id);    
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Long logIn(@RequestBody String email) {
	    	User id_user = userRepository.login(email); 
	    	if (id_user != null) 
	    		return id_user.getId();
	    	else return null;
	}
	
	@PutMapping("/update/{userId}")
	public void updateUser(@PathVariable("userId") Long id, @RequestBody User user) {
		
		userRepository.updateUser(user.getName(),user.getLastname(),user.getMobile(),
				user.getCountry(), user.getCity(), id);
	}
}
