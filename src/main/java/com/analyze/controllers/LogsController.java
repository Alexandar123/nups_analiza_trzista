package com.analyze.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.analyze.model.Log;
import com.analyze.repositories.LogRepository;

@RestController
@RequestMapping("api/logs")
public class LogsController {

	private LogRepository logRepository;

	@Autowired
	public LogsController(LogRepository logRepository) {
		this.logRepository = logRepository;
	}

	@RequestMapping(value = { "", "/" }, method = RequestMethod.GET)
	public List<Log> getAllLogs() {
		return (List<Log>) logRepository.findAll();
	}

	@RequestMapping(value = "/new", method = RequestMethod.POST)
	public Log insertNewLog(@RequestBody Log log) {
		return logRepository.save(log);
	}
	
	@RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
	public List<Log> getAllLogsByUser(@PathVariable("user_id") int user_id) {
		return logRepository.getAllLogsByUser(user_id);
	}
	
}
